package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT product_id, product_name, category_id,
                       supplier_id, unit_price, stock_quantity,
                       minimum_stock
                FROM products
                ORDER BY product_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Product product = new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id"),
                        resultSet.getDouble("unit_price"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getInt("minimum_stock")
                );

                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products.");
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> searchProducts(String keyword) {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT product_id, product_name, category_id,
                       supplier_id, unit_price, stock_quantity,
                       minimum_stock
                FROM products
                WHERE LOWER(product_name) LIKE LOWER(?)
                ORDER BY product_name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Product product = new Product(
                            resultSet.getInt("product_id"),
                            resultSet.getString("product_name"),
                            resultSet.getInt("category_id"),
                            resultSet.getInt("supplier_id"),
                            resultSet.getDouble("unit_price"),
                            resultSet.getInt("stock_quantity"),
                            resultSet.getInt("minimum_stock")
                    );

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching products.");
            e.printStackTrace();
        }

        return products;
    }

    public boolean updateStock(int productId, int newQuantity) {

        if (newQuantity < 0) {
            return false;
        }

        String sql = """
                UPDATE products
                SET stock_quantity = ?
                WHERE product_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, newQuantity);
            statement.setInt(2, productId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating product stock.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean addProduct(Product product) {

        String sql = """
                INSERT INTO products
                (product_name, category_id, supplier_id,
                 unit_price, stock_quantity, minimum_stock)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setInt(3, product.getSupplierId());
            statement.setDouble(4, product.getUnitPrice());
            statement.setInt(5, product.getStockQuantity());
            statement.setInt(6, product.getMinimumStock());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding product.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProduct(Product product) {

        String sql = """
                UPDATE products
                SET product_name = ?,
                    category_id = ?,
                    supplier_id = ?,
                    unit_price = ?,
                    minimum_stock = ?
                WHERE product_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setInt(3, product.getSupplierId());
            statement.setDouble(4, product.getUnitPrice());
            statement.setInt(5, product.getMinimumStock());
            statement.setInt(6, product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating product.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteProduct(int productId) {

        String sql = """
                DELETE FROM products
                WHERE product_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Unable to delete product."
            );

            System.out.println(
                    "The product may be referenced by existing "
                            + "orders or stock transactions."
            );

        }

        return false;
    }
}