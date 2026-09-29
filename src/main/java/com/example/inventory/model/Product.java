package com.example.inventory.model;

public class Product {

    private int productId;
    private String productName;
    private int categoryId;
    private int supplierId;
    private double unitPrice;
    private int stockQuantity;
    private int minimumStock;

    public Product() {
    }

    public Product(int productId, String productName, int categoryId,
                   int supplierId, double unitPrice,
                   int stockQuantity, int minimumStock) {

        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
        this.minimumStock = minimumStock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", unitPrice=" + unitPrice +
                ", stockQuantity=" + stockQuantity +
                ", minimumStock=" + minimumStock +
                '}';
    }
}