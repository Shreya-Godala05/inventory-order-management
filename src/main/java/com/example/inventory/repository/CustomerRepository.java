package com.example.inventory.repository;

import com.example.inventory.config.DatabaseConnection;
import com.example.inventory.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = """
                SELECT customer_id, customer_name,
                       phone, email, address
                FROM customers
                ORDER BY customer_id
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Customer customer = new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );

                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customers.");
            e.printStackTrace();
        }

        return customers;
    }

    public List<Customer> searchCustomers(String keyword) {

        List<Customer> customers = new ArrayList<>();

        String sql = """
                SELECT customer_id, customer_name,
                       phone, email, address
                FROM customers
                WHERE LOWER(customer_name) LIKE LOWER(?)
                ORDER BY customer_name
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Customer customer = new Customer(
                            resultSet.getInt("customer_id"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("phone"),
                            resultSet.getString("email"),
                            resultSet.getString("address")
                    );

                    customers.add(customer);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching customers.");
            e.printStackTrace();
        }

        return customers;
    }

    public boolean addCustomer(Customer customer) {

        String sql = """
                INSERT INTO customers
                (customer_name, phone, email, address)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding customer.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateCustomer(Customer customer) {

        String sql = """
                UPDATE customers
                SET customer_name = ?,
                    phone = ?,
                    email = ?,
                    address = ?
                WHERE customer_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());
            statement.setInt(5, customer.getCustomerId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating customer.");
            e.printStackTrace();
        }

        return false;
    }
}