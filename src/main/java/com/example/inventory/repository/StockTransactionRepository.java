package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.StockTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockTransactionRepository {

    public boolean addTransaction(StockTransaction transaction) {

        String sql = """
                INSERT INTO stock_transactions
                (product_id, user_id, transaction_type, quantity, remarks)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, transaction.getProductId());
            statement.setInt(2, transaction.getUserId());
            statement.setString(3, transaction.getTransactionType());
            statement.setInt(4, transaction.getQuantity());
            statement.setString(5, transaction.getRemarks());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding stock transaction.");
            e.printStackTrace();
        }

        return false;
    }

    public List<StockTransaction> getTransactionsByProductId(int productId) {

        List<StockTransaction> transactions = new ArrayList<>();

        String sql = """
                SELECT transaction_id, product_id, user_id,
                       transaction_type, quantity,
                       transaction_date, remarks
                FROM stock_transactions
                WHERE product_id = ?
                ORDER BY transaction_date DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    StockTransaction transaction = new StockTransaction(
                            resultSet.getInt("transaction_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("transaction_type"),
                            resultSet.getInt("quantity"),
                            resultSet.getTimestamp("transaction_date")
                                    .toLocalDateTime(),
                            resultSet.getString("remarks")
                    );

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving stock transactions.");
            e.printStackTrace();
        }

        return transactions;
    }
}