package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();

        String sql = """
                SELECT category_id, category_name, description
                FROM categories
                ORDER BY category_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Category category = new Category(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name"),
                        resultSet.getString("description")
                );

                categories.add(category);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving categories.");
            e.printStackTrace();
        }

        return categories;
    }
}