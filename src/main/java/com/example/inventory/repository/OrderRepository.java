package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    public int createOrder(Order order) {

        String sql = """
                INSERT INTO orders
                (customer_id, user_id, total_amount, status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.getCustomerId());
            statement.setInt(2, order.getUserId());
            statement.setDouble(3, order.getTotalAmount());
            statement.setString(4, order.getStatus());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                try (ResultSet keys = statement.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error creating order.");
            e.printStackTrace();
        }

        return -1;
    }

    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        String sql = """
                SELECT order_id, customer_id, user_id,
                       order_date, total_amount, status
                FROM orders
                ORDER BY order_id DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Order order = new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getTimestamp("order_date")
                                .toLocalDateTime(),
                        resultSet.getDouble("total_amount"),
                        resultSet.getString("status")
                );

                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving orders.");
            e.printStackTrace();
        }

        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {

        String sql = """
                UPDATE orders
                SET status = ?
                WHERE order_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, orderId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating order status.");
            e.printStackTrace();
        }

        return false;
    }
}