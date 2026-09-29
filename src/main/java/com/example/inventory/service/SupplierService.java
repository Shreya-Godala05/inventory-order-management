package com.example.inventory.service;

import com.example.inventory.model.Supplier;
import com.example.inventory.repository.SupplierRepository;

import java.util.List;

public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService() {
        this.supplierRepository = new SupplierRepository();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.getAllSuppliers();
    }
}