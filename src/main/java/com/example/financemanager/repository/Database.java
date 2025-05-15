package com.example.financemanager.repository;

import org.sqlite.JDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Location of database
     */
    private static final String linuxLocation = System.getProperty("user.home") + "/.financemanager/database.db";
    /**
     * Currently only table needed
     */
    private static final String requiredTable = "expenses";

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    // Remplacer la variable linuxLocation par:
    private static final String DATABASE_LOCATION = getDatabaseLocation();

    private static String getDatabaseLocation() {
        String userHome = System.getProperty("user.home");

        if (OS_NAME.contains("win")) {
            return userHome + "\\AppData\\Roaming\\FinanceManager\\database.db";
        } else if (OS_NAME.contains("mac")) {
            return userHome + "/Library/Application Support/FinanceManager/database.db";
        } else {
            return userHome + "/.financemanager/database.db";
        }
    }

    public static boolean isOK() {
        if (!ensureDirectoryExists()) return false; // échec à la création du répertoire

        if (!checkDrivers()) return false; //driver errors

        if (!checkConnection()) return false; //can't connect to db

        return createTableIfNotExists(); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createTables = """
                     CREATE TABLE IF NOT EXISTS expenses(
                          period TEXT NOT NULL,
                          total REAL NOT NULL,
                          accommodation REAL NOT NULL,
                          food REAL NOT NULL,
                          outing REAL NOT NULL,
                          transport REAL NOT NULL,
                          travel REAL NOT NULL,
                          taxes REAL NOT NULL,
                          others REAL NOT NULL
                  );
                """;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(createTables);
            statement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not find tables in database");
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + linuxLocation);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to SQLite DB at " + linuxLocation);
            return null;
        }
        return connection;
    }

    private static boolean ensureDirectoryExists() {
        String directoryPath;
        if (OS_NAME.contains("win")) {
            directoryPath = System.getProperty("user.home") + "\\AppData\\Roaming\\FinanceManager";
        } else if (OS_NAME.contains("mac")) {
            directoryPath = System.getProperty("user.home") + "/Library/Application Support/FinanceManager";
        } else {
            directoryPath = System.getProperty("user.home") + "/.financemanager";
        }

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return true;
    }

}
