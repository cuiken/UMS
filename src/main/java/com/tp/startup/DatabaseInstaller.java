package com.tp.startup;

import com.google.common.collect.Lists;
import com.tp.utils.SQLScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 上午10:56
 */
public class DatabaseInstaller {
    private Logger logger = LoggerFactory.getLogger(DatabaseInstaller.class);

    private final DatabaseProvider db;
    private final DatabaseScriptProvider scripts;
    private final String version;

    private List<String> messages = Lists.newArrayList();
    private static final String DBVERSION_PROP = "ums.database.version";

    public DatabaseInstaller(DatabaseProvider dbProvider, DatabaseScriptProvider scriptProvider) {
        db = dbProvider;
        scripts = scriptProvider;
        Properties props = new Properties();
        try {
            props.load(getClass().getResourceAsStream("/ums-version.properties"));
        } catch (IOException e) {
            logger.error("ums-version.properties not found", e);
        }

        version = props.getProperty("ums.version", "UNKNOWN");
    }

    public List<String> getMessages() {
        return messages;
    }

    public boolean isCreationRequired() {
        Connection conn = null;
        try {
            conn = db.getConnection();
            if (tableExists(conn, "acct_user") && tableExists(conn, "acct_group")) {
                return false;
            }
        } catch (Throwable t) {
            throw new RuntimeException("Error checking for tables " + t);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }
        return true;
    }

    public boolean isUpgradeRequired() {
        int desiredVersion = parseVersionString(version);
        int databaseVersion;
        try {
            databaseVersion = getDatabaseVersion();
        } catch (StartupException ex) {
            throw new RuntimeException(ex);
        }

        // if dbversion is unset then assume a new install, otherwise compare
        if (databaseVersion < 0) {
            // if this is a fresh db then we need to set the database version
            Connection con = null;
            try {
                con = db.getConnection();
                setDatabaseVersion(con, version);
            } catch (Exception ioe) {
                errorMessage("ERROR setting database version");
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception ignored) {
                }
            }

            return false;
        } else {
            return databaseVersion < desiredVersion;
        }
    }

    public void createDatabase() throws StartupException {
        logger.info("Creating UMS database tables.");

        Connection con = null;
        SQLScriptRunner create = null;
        try {
            con = db.getConnection();
//            String handle = getDatabaseHandle(con);
            create = new SQLScriptRunner(scripts.getDatabaseScript("schema.sql"));
            create.runScript(con, true);
            messages.addAll(create.getMessages());

            setDatabaseVersion(con, version);

        } catch (SQLException sqle) {
            logger.error("ERROR running SQL in database creation script", sqle);
            if (create != null) messages.addAll(create.getMessages());
            errorMessage("ERROR running SQL in database creation script");
            throw new StartupException("Error running sql script", sqle);

        } catch (Exception ioe) {
            logger.error("ERROR running database creation script", ioe);
            if (create != null) messages.addAll(create.getMessages());
            errorMessage("ERROR reading/parsing database creation script");
            throw new StartupException("Error running SQL script", ioe);

        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }

    public void upgradeDatabase(boolean runScripts) throws StartupException {
        int myVersion = parseVersionString(version);
        int dbversion = getDatabaseVersion();

        logger.debug("Database version = " + dbversion);
        logger.debug("Desired version = " + myVersion);

        Connection con = null;
        try {
            con = db.getConnection();
            if (dbversion < 0) {
                String msg = "Cannot upgrade database tables, UMS database version cannot be determined";
                errorMessage(msg);
                throw new StartupException(msg);
            } else if (dbversion >= myVersion) {
                logger.info("Database is current, no upgrade needed");
                return;
            }

            logger.info("Database is old, beginning upgrade to version " + myVersion);

            if (dbversion < 210) {
                upgradeTo210(con, runScripts);
                dbversion = 210;
            }

            updateDatabaseVersion(con, myVersion);
        } catch (SQLException e) {
            throw new StartupException("ERROR obtaining connection");
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }

    private void upgradeTo210(Connection con, boolean runScripts) {
        SQLScriptRunner runner = null;
        try {
            if (runScripts) {
//                String handle = getDatabaseHandle(con);
                String scriptPath = "200-to-210-migration.sql";
                successMessage("Running database upgrade script: " + scriptPath);
                runner = new SQLScriptRunner(scripts.getDatabaseScript(scriptPath));
                runner.runScript(con, true);
                messages.addAll(runner.getMessages());
            }

            successMessage("Doing upgrade to 210 ...");
            if (!con.getAutoCommit()) con.commit();

            successMessage("Upgrade to 210 complete.");
        } catch (Exception e) {
            logger.error("ERROR running 210 database upgrade script", e);
            if (runner != null) messages.addAll(runner.getMessages());

            errorMessage("Problem upgrading database to version 210", e);
            throw new StartupException("Problem upgrading database to version 210", e);
        }

        updateDatabaseVersion(con, 210);
    }

    private boolean tableExists(Connection con, String tableName) throws SQLException {
//        String[] types = {"TABLE"};
        ResultSet rs = con.getMetaData().getTables(null, null, "%", null);
        while (rs.next()) {
            if (tableName.toLowerCase().equals(rs.getString("TABLE_NAME").toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void errorMessage(String msg, Throwable t) {
        messages.add(msg);
        logger.error(msg, t);
    }


    private void successMessage(String msg) {
        messages.add(msg);
        logger.trace(msg);
    }

/*    public String getDatabaseHandle(Connection con) throws SQLException {

        String productName = con.getMetaData().getDatabaseProductName();
        String handle = "sql";
        if (productName.toLowerCase().indexOf("mysql") != -1) {
            handle = "mysql";
        } else if (productName.toLowerCase().indexOf("derby") != -1) {
            handle = "derby";
        } else if (productName.toLowerCase().indexOf("hsql") != -1) {
            handle = "hsqldb";
        } else if (productName.toLowerCase().indexOf("postgres") != -1) {
            handle = "postgresql";
        } else if (productName.toLowerCase().indexOf("oracle") != -1) {
            handle = "oracle";
        } else if (productName.toLowerCase().indexOf("microsoft") != -1) {
            handle = "mssql";
        } else if (productName.toLowerCase().indexOf("db2") != -1) {
            handle = "db2";
        }

        return handle;
    }*/

    private int getDatabaseVersion() throws StartupException {
        int dbversion = -1;

        // get the current db version
        Connection con = null;
        try {
            con = db.getConnection();
            Statement stmt = con.createStatement();

            // just check in the ums_properties table
            ResultSet rs = stmt.executeQuery(
                    "select value from ums_properties where name = '" + DBVERSION_PROP + "'");

            if (rs.next()) {
                dbversion = Integer.parseInt(rs.getString(1));

            } else {
                // tough to know if this is an upgrade with no db version :/
                // however, if ums_properties is not empty then we at least
                // we have someone upgrading from 1.2.x
                rs = stmt.executeQuery("select count(*) from ums_properties");
                if (rs.next()) {
                    if (rs.getInt(1) > 0)
                        dbversion = 120;
                }
            }

        } catch (Exception e) {
            // that's strange ... hopefully we didn't need to upgrade
            logger.error("Couldn't lookup current database version", e);
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
        return dbversion;
    }

    private int parseVersionString(String vstring) {
        int myversion = 0;

        // NOTE: this assumes a maximum of 3 digits for the version number
        // so if we get to 10.0 then we'll need to upgrade this

        // strip out non-digits
        vstring = vstring.replaceAll("\\Q.\\E", "");
        vstring = vstring.replaceAll("\\D", "");
        if (vstring.length() > 3)
            vstring = vstring.substring(0, 3);

        // parse to an int
        try {
            int parsed = Integer.parseInt(vstring);
            if (parsed < 100) myversion = parsed * 10;
            else myversion = parsed;
        } catch (Exception e) {
        }

        return myversion;
    }

    /**
     * Insert a new database.version property.
     * This should only be called once for new installations
     */
    private void setDatabaseVersion(Connection con, String version)
            throws StartupException {
        setDatabaseVersion(con, parseVersionString(version));
    }

    /**
     * Insert a new database.version property.
     * This should only be called once for new installations
     */
    private void setDatabaseVersion(Connection con, int version)
            throws StartupException {

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into ums_properties " +
                    "values('" + DBVERSION_PROP + "', '" + version + "')");

            logger.debug("Set database verstion to " + version);
        } catch (SQLException se) {
            throw new StartupException("Error setting database version.", se);
        }
    }

    private void updateDatabaseVersion(Connection con, int version)
            throws StartupException {

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("update ums_properties " +
                    "set value = '" + version + "'" +
                    "where name = '" + DBVERSION_PROP + "'");

            logger.debug("Updated database verstion to " + version);
        } catch (SQLException se) {
            throw new StartupException("Error setting database version.", se);
        }
    }

    private void errorMessage(String msg) {
        messages.add(msg);
        logger.error(msg);
    }
}
