package com.example.inventory.model;

import java.time.LocalDateTime;

public class StockTransaction {

    private int transactionId;
    private int productId;
    private int userId;
    private String transactionType;
    private int quantity;
    private LocalDateTime transactionDate;
    private String remarks;

    public StockTransaction() {
    }

    public StockTransaction(int transactionId, int productId, int userId,
                            String transactionType, int quantity,
                            LocalDateTime transactionDate, String remarks) {

        this.transactionId = transactionId;
        this.productId = productId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.remarks = remarks;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
                "transactionId=" + transactionId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", transactionType='" + transactionType + '\'' +
                ", quantity=" + quantity +
                ", transactionDate=" + transactionDate +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}