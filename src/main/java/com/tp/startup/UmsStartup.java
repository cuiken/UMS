package com.tp.startup;


import com.tp.utils.UmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class UmsStartup {

    private static Logger log = LoggerFactory.getLogger(UmsStartup.class);

    private static boolean prepared = false;
    private static DatabaseProvider dbProvider = null;

    private UmsStartup() {
    }

    public static boolean isPrepared() {
        return prepared;
    }

    private static DatabaseInstaller getDatabaseInstaller() {
        return new DatabaseInstaller(getDatabaseProvider(), new ClasspathDatabaseScriptProvider());
    }

    public static List<String> upgradeDatabase(boolean runScripts)
            throws StartupException {

        DatabaseInstaller installer = getDatabaseInstaller();
        try {
            installer.upgradeDatabase(true);

            // any time we've successfully upgraded a db we are prepared
            prepared = true;

        } catch (StartupException se) {
            throw new StartupException(se.getMessage(), installer.getMessages());
        }

        return installer.getMessages();
    }

    public static boolean isDatabaseUpgradeRequired() {
        return getDatabaseInstaller().isUpgradeRequired();
    }

    public static boolean isDatabaseCreationRequired() {
        return getDatabaseInstaller().isCreationRequired();
    }

    public static DatabaseProvider getDatabaseProvider() {
        if (dbProvider == null) {
            throw new IllegalStateException("UMS has not been prepared yet");
        }
        return dbProvider;
    }


    /**
     * Run database creation scripts.
     */
    public static List<String> createDatabase() throws StartupException {

        DatabaseInstaller installer = getDatabaseInstaller();
        try {
            installer.createDatabase();

            // any time we've successfully installed a db we are prepared
            prepared = true;

        } catch (StartupException se) {
            throw new StartupException(se.getMessage(), installer.getMessages());
        }

        return installer.getMessages();
    }

    public static void prepare() throws StartupException {
        try {
            dbProvider = new DatabaseProvider();
        } catch (StartupException ex) {
            throw ex;
        }
        if ("manual".equals(UmsConfig.getProperty("installation.type"))) {
            DatabaseInstaller dbInstaller = getDatabaseInstaller();
            if (dbInstaller.isUpgradeRequired()) {
                dbInstaller.upgradeDatabase(false);
            }
            prepared = true;
        } else {
            DatabaseInstaller dbInstaller = getDatabaseInstaller();
            if (!dbInstaller.isCreationRequired() &&
                    !dbInstaller.isUpgradeRequired()) {
                prepared = true;
            }
            if (dbInstaller.isUpgradeRequired()) {
                dbInstaller.upgradeDatabase(true);
            }
        }
    }

}
