package com.worldpay.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtil.class);

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private static String CREATE_TABLE = "CREATE TABLE merchant_offer(id UUID PRIMARY KEY, " +
            "name VARCHAR(255) , " +
            "description VARCHAR(255), " +
            "is_service BOOL  , " +
            "conditions VARCHAR(255) , " +
            "price DECIMAL, " +
            "currency VARCHAR(255))  ";
    private static String DROP_TABLE = "DROP TABLE merchant_offer";

    private DBUtil() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("", e);
        }
        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("", e.getMessage());
        }
        return connection;
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("", e.getMessage());
        }
    }

    public static void createTable(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            LOGGER.error("", e.getMessage());
        }
    }

    public static void dropTable(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(DROP_TABLE);
        } catch (SQLException e) {
            LOGGER.error("", e.getMessage());
        }
    }
}
