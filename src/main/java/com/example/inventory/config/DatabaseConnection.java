package com.example.inventory.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final Properties properties =
            new Properties();

    static {

        try (InputStream input =
                     DatabaseConnection.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "application-local.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Database configuration file not found."
                );
            }

            properties.load(input);

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Unable to load database configuration.",
                    e
            );
        }
    }

    public static Connection getConnection()
            throws SQLException {

        String url =
                properties.getProperty("db.url");

        String username =
                properties.getProperty("db.username");

        String password =
                properties.getProperty("db.password");

        if (url == null ||
                username == null ||
                password == null) {

            throw new IllegalStateException(
                    "Database configuration is incomplete."
            );
        }

        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}