package com.example.inventory.service;

import com.example.inventory.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportService {

    public int getTotalOrders() {
        return getCount(
                "SELECT COUNT(*) FROM orders"
        );
    }

    public int getPendingOrders() {
        return getCount(
                "SELECT COUNT(*) FROM orders WHERE status = 'PENDING'"
        );
    }

    public int getConfirmedOrders() {
        return getCount(
                "SELECT COUNT(*) FROM orders WHERE status = 'CONFIRMED'"
        );
    }

    public int getCompletedOrders() {
        return getCount(
                "SELECT COUNT(*) FROM orders WHERE status = 'COMPLETED'"
        );
    }

    public int getCancelledOrders() {
        return getCount(
                "SELECT COUNT(*) FROM orders WHERE status = 'CANCELLED'"
        );
    }

    public double getTotalSales() {
        String sql = """
                SELECT COALESCE(SUM(total_amount), 0)
                FROM orders
                WHERE status IN ('CONFIRMED', 'COMPLETED')
                """;

        return getDouble(sql);
    }

    public double getInventoryValue() {
        String sql = """
                SELECT COALESCE(
                    SUM(unit_price * stock_quantity),
                    0
                )
                FROM products
                """;

        return getDouble(sql);
    }

    public int getLowStockCount() {
        return getCount(
                """
                SELECT COUNT(*)
                FROM products
                WHERE stock_quantity <= minimum_stock
                """
        );
    }

    public int getOutOfStockCount() {
        return getCount(
                """
                SELECT COUNT(*)
                FROM products
                WHERE stock_quantity = 0
                """
        );
    }

    private int getCount(String sql) {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error generating report."
            );
            e.printStackTrace();
        }

        return 0;
    }

    private double getDouble(String sql) {

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error generating report."
            );
            e.printStackTrace();
        }

        return 0.0;
    }
}