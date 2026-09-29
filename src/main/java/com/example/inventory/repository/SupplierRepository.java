package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepository {

    public List<Supplier> getAllSuppliers() {

        List<Supplier> suppliers = new ArrayList<>();

        String sql = """
                SELECT supplier_id, supplier_name, contact_person,
                       phone, email, address
                FROM suppliers
                ORDER BY supplier_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Supplier supplier = new Supplier(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("contact_person"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );

                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving suppliers.");
            e.printStackTrace();
        }

        return suppliers;
    }
}