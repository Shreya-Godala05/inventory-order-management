package com.example.inventory.service;

import com.example.inventory.model.Order;
import com.example.inventory.model.OrderItem;
import com.example.inventory.model.StockTransaction;
import com.example.inventory.repository.OrderItemRepository;
import com.example.inventory.repository.OrderRepository;

import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StockService stockService;

    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.orderItemRepository = new OrderItemRepository();
        this.stockService = new StockService();
    }

    public int createOrder(Order order) {

        if (order == null) {
            return -1;
        }

        if (order.getCustomerId() <= 0 ||
                order.getUserId() <= 0) {
            return -1;
        }

        if (order.getTotalAmount() < 0) {
            return -1;
        }

        if (order.getStatus() == null ||
                order.getStatus().trim().isEmpty()) {
            order.setStatus("PENDING");
        }

        return orderRepository.createOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public boolean updateOrderStatus(
            int orderId,
            String status) {

        if (orderId <= 0 ||
                status == null ||
                status.trim().isEmpty()) {
            return false;
        }

        return orderRepository.updateOrderStatus(
                orderId,
                status.trim().toUpperCase()
        );
    }

    public boolean confirmOrder(int orderId) {

        Order order = findOrder(orderId);

        if (order == null) {
            return false;
        }

        if (!"PENDING".equalsIgnoreCase(
                order.getStatus())) {
            return false;
        }

        return updateOrderStatus(
                orderId,
                "CONFIRMED"
        );
    }

    public boolean completeOrder(int orderId) {

        Order order = findOrder(orderId);

        if (order == null) {
            return false;
        }

        if (!"CONFIRMED".equalsIgnoreCase(
                order.getStatus())) {
            return false;
        }

        return updateOrderStatus(
                orderId,
                "COMPLETED"
        );
    }

    public boolean cancelOrder(
            int orderId,
            int userId) {

        if (orderId <= 0 || userId <= 0) {
            return false;
        }

        Order order = findOrder(orderId);

        if (order == null) {
            return false;
        }

        if (!"PENDING".equalsIgnoreCase(
                order.getStatus())) {
            return false;
        }

        List<OrderItem> items =
                getOrderItems(orderId);

        for (OrderItem item : items) {

            boolean saleRecorded =
                    hasSaleTransaction(
                            item.getProductId(),
                            orderId
                    );

            if (!saleRecorded) {
                continue;
            }

            boolean restored =
                    stockService.restoreStock(
                            item.getProductId(),
                            item.getQuantity(),
                            userId,
                            "Stock restored for cancelled Order #"
                                    + orderId
                    );

            if (!restored) {
                return false;
            }
        }

        return updateOrderStatus(
                orderId,
                "CANCELLED"
        );
    }

    public boolean addOrderItem(OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderId() <= 0 ||
                orderItem.getProductId() <= 0) {
            return false;
        }

        if (orderItem.getQuantity() <= 0 ||
                orderItem.getUnitPrice() < 0 ||
                orderItem.getSubtotal() < 0) {
            return false;
        }

        return orderItemRepository.addOrderItem(orderItem);
    }

    public boolean addOrderItemAndUpdateStock(
            OrderItem orderItem,
            int userId) {

        if (orderItem == null || userId <= 0) {
            return false;
        }

        boolean itemAdded =
                addOrderItem(orderItem);

        if (!itemAdded) {
            return false;
        }

        return stockService.decreaseStock(
                orderItem.getProductId(),
                orderItem.getQuantity(),
                userId,
                "Sale for Order #" +
                        orderItem.getOrderId()
        );
    }

    public List<OrderItem> getOrderItems(int orderId) {

        if (orderId <= 0) {
            return List.of();
        }

        return orderItemRepository
                .getItemsByOrderId(orderId);
    }

    public double calculateSubtotal(
            double unitPrice,
            int quantity) {

        if (unitPrice < 0 || quantity <= 0) {
            return 0;
        }

        return unitPrice * quantity;
    }

    private boolean hasSaleTransaction(
            int productId,
            int orderId) {

        List<StockTransaction> transactions =
                stockService.getProductTransactions(
                        productId
                );

        String expectedRemark =
                "Sale for Order #" + orderId;

        return transactions.stream()
                .anyMatch(transaction ->
                        "SALE".equalsIgnoreCase(
                                transaction.getTransactionType()
                        )
                        &&
                        expectedRemark.equals(
                                transaction.getRemarks()
                        )
                );
    }

    private Order findOrder(int orderId) {

        return orderRepository.getAllOrders()
                .stream()
                .filter(order ->
                        order.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }
}