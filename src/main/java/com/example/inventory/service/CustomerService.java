package com.example.inventory.service;

import com.example.inventory.model.Customer;
import com.example.inventory.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    public List<Customer> searchCustomers(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCustomers();
        }

        return customerRepository.searchCustomers(keyword.trim());
    }

    public boolean addCustomer(Customer customer) {

        if (customer == null) {
            return false;
        }

        if (customer.getCustomerName() == null ||
                customer.getCustomerName().trim().isEmpty()) {
            return false;
        }

        return customerRepository.addCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {

        if (customer == null ||
                customer.getCustomerId() <= 0) {
            return false;
        }

        if (customer.getCustomerName() == null ||
                customer.getCustomerName().trim().isEmpty()) {
            return false;
        }

        return customerRepository.updateCustomer(customer);
    }
}