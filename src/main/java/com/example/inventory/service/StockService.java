package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.model.StockTransaction;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.StockTransactionRepository;

import java.util.List;

public class StockService {

    private final StockTransactionRepository stockTransactionRepository;
    private final ProductRepository productRepository;

    public StockService() {
        this.stockTransactionRepository = new StockTransactionRepository();
        this.productRepository = new ProductRepository();
    }

    public boolean recordStockTransaction(StockTransaction transaction) {

        if (transaction == null) {
            return false;
        }

        if (transaction.getProductId() <= 0 ||
                transaction.getUserId() <= 0) {
            return false;
        }

        if (transaction.getQuantity() <= 0) {
            return false;
        }

        if (transaction.getTransactionType() == null ||
                transaction.getTransactionType().trim().isEmpty()) {
            return false;
        }

        return stockTransactionRepository.addTransaction(transaction);
    }

    public List<StockTransaction> getProductTransactions(int productId) {

        if (productId <= 0) {
            return List.of();
        }

        return stockTransactionRepository
                .getTransactionsByProductId(productId);
    }

    public boolean updateStock(int productId, int newQuantity) {

        if (productId <= 0 || newQuantity < 0) {
            return false;
        }

        return productRepository.updateStock(
                productId,
                newQuantity
        );
    }

    public boolean increaseStock(
            int productId,
            int quantity,
            int userId,
            String remarks) {

        if (productId <= 0 ||
                quantity <= 0 ||
                userId <= 0) {
            return false;
        }

        Product product = findProduct(productId);

        if (product == null) {
            return false;
        }

        int newQuantity =
                product.getStockQuantity() + quantity;

        boolean stockUpdated =
                updateStock(productId, newQuantity);

        if (!stockUpdated) {
            return false;
        }

        StockTransaction transaction =
                new StockTransaction(
                        0,
                        productId,
                        userId,
                        "STOCK_IN",
                        quantity,
                        null,
                        remarks
                );

        return recordStockTransaction(transaction);
    }

    public boolean decreaseStock(
            int productId,
            int quantity,
            int userId,
            String remarks) {

        if (productId <= 0 ||
                quantity <= 0 ||
                userId <= 0) {
            return false;
        }

        Product product = findProduct(productId);

        if (product == null) {
            return false;
        }

        if (quantity > product.getStockQuantity()) {
            return false;
        }

        int newQuantity =
                product.getStockQuantity() - quantity;

        boolean stockUpdated =
                updateStock(productId, newQuantity);

        if (!stockUpdated) {
            return false;
        }

        StockTransaction transaction =
                new StockTransaction(
                        0,
                        productId,
                        userId,
                        "SALE",
                        quantity,
                        null,
                        remarks
                );

        return recordStockTransaction(transaction);
    }

    public boolean restoreStock(
            int productId,
            int quantity,
            int userId,
            String remarks) {

        if (productId <= 0 ||
                quantity <= 0 ||
                userId <= 0) {
            return false;
        }

        Product product = findProduct(productId);

        if (product == null) {
            return false;
        }

        int newQuantity =
                product.getStockQuantity() + quantity;

        boolean stockUpdated =
                updateStock(productId, newQuantity);

        if (!stockUpdated) {
            return false;
        }

        StockTransaction transaction =
                new StockTransaction(
                        0,
                        productId,
                        userId,
                        "ADJUSTMENT",
                        quantity,
                        null,
                        remarks
                );

        return recordStockTransaction(transaction);
    }

    private Product findProduct(int productId) {

        return productRepository.getAllProducts()
                .stream()
                .filter(product ->
                        product.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }
}