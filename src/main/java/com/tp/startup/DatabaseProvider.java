package com.tp.startup;

import com.google.common.collect.Lists;
import com.tp.utils.PropertiesLoader;
import com.tp.utils.UmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * User: ken.cui
 * Date: 13-3-22
 * Time: 上午9:56
 */
public class DatabaseProvider {
    private static Logger logger = LoggerFactory.getLogger(DatabaseProvider.class);

    public enum ConfigurationType {JDBC_PROPERTIES;}

    private List<String> startupLog = Lists.newArrayList();

    private ConfigurationType type = ConfigurationType.JDBC_PROPERTIES;

    private String jdbcDriverClass = null;
    private String jdbcConnectionURL = null;
    private String jdbcUsername = null;
    private String jdbcPassword = null;
    private Properties props = null;

    public DatabaseProvider() throws StartupException {
        jdbcDriverClass = UmsConfig.getProperty("jdbc.driver");
        jdbcConnectionURL = UmsConfig.getProperty("jdbc.url");
        jdbcUsername = UmsConfig.getProperty("jdbc.username");
        jdbcPassword = UmsConfig.getProperty("jdbc.password");

        successMessage("SUCCESS: Got parameters");
        successMessage("-- Using JDBC driver class: " + jdbcDriverClass);
        successMessage("-- Using JDBC connection URL: " + jdbcConnectionURL);
        successMessage("-- Using JDBC username: " + jdbcUsername);
        successMessage("-- Using JDBC password: [hidden]");
        try {
            Class.forName(getJdbcDriverClass());
        } catch (ClassNotFoundException e) {
            String errorMsg =
                    "ERROR: cannot load JDBC driver class [" + getJdbcDriverClass() + "]. "
                            + "Likely problem: JDBC driver jar missing from server classpath.";
            errorMessage(errorMsg);
            throw new StartupException(errorMsg, e, startupLog);
        }
        successMessage("SUCCESS: loaded JDBC driver class [" + getJdbcDriverClass() + "]");

        if (getJdbcUsername() != null || getJdbcPassword() != null) {
            props = new Properties();
            if (getJdbcUsername() != null) props.put("user", getJdbcUsername());
            if (getJdbcPassword() != null) props.put("password", getJdbcPassword());
        }
        try {
            Connection testcon = getConnection();
            testcon.close();
        } catch (Throwable t) {
            String errorMsg =
                    "ERROR: unable to obtain database connection. "
                            + "Likely problem: bad connection parameters or database unavailable.";
            errorMessage(errorMsg);
            throw new StartupException(errorMsg, t, startupLog);
        }

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getJdbcConnectionURL(), props);
    }

    private void successMessage(String msg) {
        startupLog.add(msg);
        logger.info(msg);
    }

    private void errorMessage(String msg) {
        startupLog.add(msg);
        logger.error(msg);
    }

    public String getJdbcDriverClass() {
        return jdbcDriverClass;
    }

    public String getJdbcConnectionURL() {
        return jdbcConnectionURL;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public List<String> getStartupLog() {
        return startupLog;
    }
}
