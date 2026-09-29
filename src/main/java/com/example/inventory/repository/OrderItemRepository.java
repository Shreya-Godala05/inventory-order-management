package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository {

    public boolean addOrderItem(OrderItem orderItem) {

        String sql = """
                INSERT INTO order_items
                (order_id, product_id, quantity, unit_price, subtotal)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setDouble(4, orderItem.getUnitPrice());
            statement.setDouble(5, orderItem.getSubtotal());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding order item.");
            e.printStackTrace();
        }

        return false;
    }

    public List<OrderItem> getItemsByOrderId(int orderId) {

        List<OrderItem> items = new ArrayList<>();

        String sql = """
                SELECT order_item_id, order_id, product_id,
                       quantity, unit_price, subtotal
                FROM order_items
                WHERE order_id = ?
                ORDER BY order_item_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    OrderItem item = new OrderItem(
                            resultSet.getInt("order_item_id"),
                            resultSet.getInt("order_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getDouble("subtotal")
                    );

                    items.add(item);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving order items.");
            e.printStackTrace();
        }

        return items;
    }
}