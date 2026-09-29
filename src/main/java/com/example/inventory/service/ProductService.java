package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService() {
        this.productRepository =
                new ProductRepository();
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public List<Product> searchProducts(
            String keyword) {

        if (keyword == null ||
                keyword.trim().isEmpty()) {

            return getAllProducts();
        }

        return productRepository.searchProducts(
                keyword.trim()
        );
    }

    public List<Product> getLowStockProducts() {

        return getAllProducts()
                .stream()
                .filter(product ->
                        product.getStockQuantity()
                                <= product.getMinimumStock())
                .toList();
    }

    public boolean isOutOfStock(
            Product product) {

        return product != null &&
                product.getStockQuantity() == 0;
    }

    public int getRecommendedReorderQuantity(
            Product product) {

        if (product == null) {
            return 0;
        }

        int currentStock =
                product.getStockQuantity();

        int minimumStock =
                product.getMinimumStock();

        if (currentStock > minimumStock) {
            return 0;
        }

        /*
         * Target stock is twice the minimum level.
         * Example:
         * Minimum = 10
         * Current = 4
         * Recommended reorder = 16
         */
        int targetStock =
                minimumStock * 2;

        return Math.max(
                0,
                targetStock - currentStock
        );
    }

    public boolean addProduct(
            Product product) {

        if (product == null) {
            return false;
        }

        if (product.getProductName() == null ||
                product.getProductName()
                        .trim()
                        .isEmpty()) {
            return false;
        }

        if (product.getCategoryId() <= 0 ||
                product.getSupplierId() <= 0) {
            return false;
        }

        if (product.getUnitPrice() < 0 ||
                product.getStockQuantity() < 0 ||
                product.getMinimumStock() < 0) {
            return false;
        }

        return productRepository.addProduct(
                product
        );
    }

    public boolean updateProduct(
            Product product) {

        if (product == null ||
                product.getProductId() <= 0) {
            return false;
        }

        if (product.getProductName() == null ||
                product.getProductName()
                        .trim()
                        .isEmpty()) {
            return false;
        }

        if (product.getCategoryId() <= 0 ||
                product.getSupplierId() <= 0) {
            return false;
        }

        if (product.getUnitPrice() < 0 ||
                product.getMinimumStock() < 0) {
            return false;
        }

        return productRepository.updateProduct(
                product
        );
    }

    public boolean deleteProduct(
            int productId) {

        if (productId <= 0) {
            return false;
        }

        return productRepository.deleteProduct(
                productId
        );
    }
}