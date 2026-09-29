package com.example.inventory.ui;

import com.example.inventory.model.Customer;
import com.example.inventory.model.Order;
import com.example.inventory.model.OrderItem;
import com.example.inventory.model.Product;
import com.example.inventory.model.StockTransaction;
import com.example.inventory.model.User;
import com.example.inventory.service.CustomerService;
import com.example.inventory.service.OrderService;
import com.example.inventory.service.ProductService;
import com.example.inventory.service.ReportService;
import com.example.inventory.service.StockService;
import com.example.inventory.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;
    private final UserService userService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final StockService stockService;
    private final ReportService reportService;

    private User loggedInUser;

    public ConsoleUI() {

        scanner = new Scanner(System.in);

        userService = new UserService();
        productService = new ProductService();
        customerService = new CustomerService();
        orderService = new OrderService();
        stockService = new StockService();
        reportService = new ReportService();
    }

    public void start() {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "   INVENTORY & ORDER MANAGEMENT"
        );

        System.out.println(
                "======================================"
        );

        if (!login()) {

            System.out.println(
                    "Too many failed login attempts."
            );

            return;
        }

        showMainMenu();
    }

    private boolean login() {

        for (int attempt = 1;
             attempt <= 3;
             attempt++) {

            System.out.println(
                    "\n---------- LOGIN ----------"
            );

            System.out.print("Username: ");
            String username =
                    scanner.nextLine().trim();

            System.out.print("Password: ");
            String password =
                    scanner.nextLine();

            User user =
                    userService.authenticate(
                            username,
                            password
                    );

            if (user != null) {

                loggedInUser = user;

                System.out.println(
                        "\nLogin successful!"
                );

                System.out.println(
                        "Welcome, " +
                                user.getUsername()
                );

                System.out.println(
                        "Role: " +
                                user.getRole()
                );

                return true;
            }

            System.out.println(
                    "Invalid username or password."
            );
        }

        return false;
    }

    private void showMainMenu() {

        while (true) {

            System.out.println(
                    "\n======================================"
            );

            System.out.println(
                    "              MAIN MENU"
            );

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "1. View All Products"
            );

            System.out.println(
                    "2. Search Product"
            );

            System.out.println(
                    "3. View Low Stock Products"
            );

            System.out.println(
                    "4. View Out-of-Stock Products"
            );

            System.out.println(
                    "5. Add Product"
            );

            System.out.println(
                    "6. Edit Product"
            );

            System.out.println(
                    "7. Delete Product"
            );

            System.out.println(
                    "8. View Customers"
            );

            System.out.println(
                    "9. Search Customer"
            );

            System.out.println(
                    "10. Add Customer"
            );

            System.out.println(
                    "11. Edit Customer"
            );

            System.out.println(
                    "12. Create Order"
            );

            System.out.println(
                    "13. View Orders"
            );

            System.out.println(
                    "14. Confirm Order"
            );

            System.out.println(
                    "15. Complete Order"
            );

            System.out.println(
                    "16. Cancel Order"
            );

            System.out.println(
                    "17. Receive Stock"
            );

            System.out.println(
                    "18. View Stock Transactions"
            );

            System.out.println(
                    "19. Reorder Recommendations"
            );

            System.out.println(
                    "20. Reports"
            );

            System.out.println(
                    "21. Exit"
            );

            System.out.print(
                    "\nEnter your choice: "
            );

            int choice = readInt();

            switch (choice) {

                case 1:
                    viewAllProducts();
                    break;

                case 2:
                    searchProduct();
                    break;

                case 3:
                    viewLowStockProducts();
                    break;

                case 4:
                    viewOutOfStockProducts();
                    break;

                case 5:
                    addProduct();
                    break;

                case 6:
                    editProduct();
                    break;

                case 7:
                    deleteProduct();
                    break;

                case 8:
                    viewCustomers();
                    break;

                case 9:
                    searchCustomer();
                    break;

                case 10:
                    addCustomer();
                    break;

                case 11:
                    editCustomer();
                    break;

                case 12:
                    createOrder();
                    break;

                case 13:
                    viewOrders();
                    break;

                case 14:
                    confirmOrder();
                    break;

                case 15:
                    completeOrder();
                    break;

                case 16:
                    cancelOrder();
                    break;

                case 17:
                    receiveStock();
                    break;

                case 18:
                    viewStockTransactions();
                    break;

                case 19:
                    showReorderRecommendations();
                    break;

                case 20:
                    showReports();
                    break;

                case 21:

                    System.out.println(
                            "\nThank you for using the system!"
                    );

                    return;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    private void viewAllProducts() {

        System.out.println(
                "\n---------- ALL PRODUCTS ----------"
        );

        List<Product> products =
                productService.getAllProducts();

        if (products.isEmpty()) {

            System.out.println(
                    "No products found."
            );

            return;
        }

        printProducts(products);
    }

    private void searchProduct() {

        System.out.print(
                "\nEnter product name to search: "
        );

        String keyword =
                scanner.nextLine();

        List<Product> products =
                productService.searchProducts(
                        keyword
                );

        System.out.println(
                "\n---------- SEARCH RESULTS ----------"
        );

        if (products.isEmpty()) {

            System.out.println(
                    "No products found."
            );

            return;
        }

        printProducts(products);
    }

    private void viewLowStockProducts() {

        System.out.println(
                "\n---------- LOW STOCK PRODUCTS ----------"
        );

        List<Product> products =
                productService.getLowStockProducts();

        if (products.isEmpty()) {

            System.out.println(
                    "No low-stock products."
            );

            return;
        }

        printProducts(products);
    }

    private void viewOutOfStockProducts() {

        System.out.println(
                "\n---------- OUT-OF-STOCK PRODUCTS ----------"
        );

        List<Product> products =
                productService.getAllProducts()
                        .stream()
                        .filter(product ->
                                product.getStockQuantity()
                                        == 0)
                        .toList();

        if (products.isEmpty()) {

            System.out.println(
                    "No out-of-stock products."
            );

            return;
        }

        printProducts(products);
    }

    private void printProducts(
            List<Product> products) {

        System.out.printf(
                "%-5s %-25s %-10s %-10s %-10s%n",
                "ID",
                "Product",
                "Price",
                "Stock",
                "Min"
        );

        System.out.println(
                "------------------------------------------------------------"
        );

        for (Product product : products) {

            System.out.printf(
                    "%-5d %-25s ₹%-9.2f %-10d %-10d%n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getUnitPrice(),
                    product.getStockQuantity(),
                    product.getMinimumStock()
            );
        }
    }

    private void addProduct() {

        System.out.println(
                "\n---------- ADD PRODUCT ----------"
        );

        System.out.print(
                "Product name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Category ID: "
        );

        int categoryId =
                readInt();

        System.out.print(
                "Supplier ID: "
        );

        int supplierId =
                readInt();

        System.out.print(
                "Unit price: "
        );

        double price =
                readDouble();

        System.out.print(
                "Initial stock quantity: "
        );

        int stock =
                readInt();

        System.out.print(
                "Minimum stock level: "
        );

        int minimumStock =
                readInt();

        Product product =
                new Product(
                        0,
                        name,
                        categoryId,
                        supplierId,
                        price,
                        stock,
                        minimumStock
                );

        boolean success =
                productService.addProduct(
                        product
                );

        System.out.println(
                success
                        ? "Product added successfully."
                        : "Unable to add product."
        );
    }

    private void editProduct() {

        System.out.println(
                "\n---------- EDIT PRODUCT ----------"
        );

        viewAllProducts();

        System.out.print(
                "\nEnter Product ID to edit: "
        );

        int productId =
                readInt();

        Product existing =
                productService.getAllProducts()
                        .stream()
                        .filter(product ->
                                product.getProductId()
                                        == productId)
                        .findFirst()
                        .orElse(null);

        if (existing == null) {

            System.out.println(
                    "Product not found."
            );

            return;
        }

        System.out.print(
                "Product name [" +
                        existing.getProductName() +
                        "]: "
        );

        String name =
                scanner.nextLine();

        if (name.trim().isEmpty()) {
            name = existing.getProductName();
        }

        System.out.print(
                "Category ID [" +
                        existing.getCategoryId() +
                        "]: "
        );

        String categoryInput =
                scanner.nextLine();

        int categoryId =
                categoryInput.trim().isEmpty()
                        ? existing.getCategoryId()
                        : Integer.parseInt(
                                categoryInput
                        );

        System.out.print(
                "Supplier ID [" +
                        existing.getSupplierId() +
                        "]: "
        );

        String supplierInput =
                scanner.nextLine();

        int supplierId =
                supplierInput.trim().isEmpty()
                        ? existing.getSupplierId()
                        : Integer.parseInt(
                                supplierInput
                        );

        System.out.print(
                "Unit price [" +
                        existing.getUnitPrice() +
                        "]: "
        );

        String priceInput =
                scanner.nextLine();

        double price =
                priceInput.trim().isEmpty()
                        ? existing.getUnitPrice()
                        : Double.parseDouble(
                                priceInput
                        );

        System.out.print(
                "Minimum stock [" +
                        existing.getMinimumStock() +
                        "]: "
        );

        String minimumInput =
                scanner.nextLine();

        int minimumStock =
                minimumInput.trim().isEmpty()
                        ? existing.getMinimumStock()
                        : Integer.parseInt(
                                minimumInput
                        );

        Product updated =
                new Product(
                        productId,
                        name,
                        categoryId,
                        supplierId,
                        price,
                        existing.getStockQuantity(),
                        minimumStock
                );

        boolean success =
                productService.updateProduct(
                        updated
                );

        System.out.println(
                success
                        ? "Product updated successfully."
                        : "Unable to update product."
        );
    }

    private void deleteProduct() {

        if (!userService.isAdmin(loggedInUser)) {

            System.out.println(
                    "Only administrators can delete products."
            );

            return;
        }

        viewAllProducts();

        System.out.print(
                "\nEnter Product ID to delete: "
        );

        int productId =
                readInt();

        System.out.print(
                "Are you sure? (Y/N): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Delete cancelled."
            );

            return;
        }

        boolean success =
                productService.deleteProduct(
                        productId
                );

        System.out.println(
                success
                        ? "Product deleted successfully."
                        : "Unable to delete product."
        );
    }

    private void viewCustomers() {

        System.out.println(
                "\n---------- CUSTOMERS ----------"
        );

        List<Customer> customers =
                customerService.getAllCustomers();

        if (customers.isEmpty()) {

            System.out.println(
                    "No customers found."
            );

            return;
        }

        printCustomers(customers);
    }

    private void searchCustomer() {

        System.out.print(
                "\nEnter customer name to search: "
        );

        String keyword =
                scanner.nextLine();

        List<Customer> customers =
                customerService.searchCustomers(
                        keyword
                );

        if (customers.isEmpty()) {

            System.out.println(
                    "No customers found."
            );

            return;
        }

        printCustomers(customers);
    }

    private void printCustomers(
            List<Customer> customers) {

        System.out.printf(
                "%-5s %-25s %-15s %-30s%n",
                "ID",
                "Name",
                "Phone",
                "Email"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Customer customer :
                customers) {

            System.out.printf(
                    "%-5d %-25s %-15s %-30s%n",
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getPhone(),
                    customer.getEmail()
            );
        }
    }

    private void addCustomer() {

        System.out.println(
                "\n---------- ADD CUSTOMER ----------"
        );

        System.out.print(
                "Customer name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Phone: "
        );

        String phone =
                scanner.nextLine();

        System.out.print(
                "Email: "
        );

        String email =
                scanner.nextLine();

        System.out.print(
                "Address: "
        );

        String address =
                scanner.nextLine();

        Customer customer =
                new Customer(
                        0,
                        name,
                        phone,
                        email,
                        address
                );

        boolean success =
                customerService.addCustomer(
                        customer
                );

        System.out.println(
                success
                        ? "Customer added successfully."
                        : "Unable to add customer."
        );
    }

    private void editCustomer() {

        viewCustomers();

        System.out.print(
                "\nEnter Customer ID to edit: "
        );

        int customerId =
                readInt();

        Customer existing =
                customerService.getAllCustomers()
                        .stream()
                        .filter(customer ->
                                customer.getCustomerId()
                                        == customerId)
                        .findFirst()
                        .orElse(null);

        if (existing == null) {

            System.out.println(
                    "Customer not found."
            );

            return;
        }

        System.out.print(
                "Customer name [" +
                        existing.getCustomerName() +
                        "]: "
        );

        String name =
                scanner.nextLine();

        if (name.trim().isEmpty()) {
            name = existing.getCustomerName();
        }

        System.out.print(
                "Phone [" +
                        existing.getPhone() +
                        "]: "
        );

        String phone =
                scanner.nextLine();

        if (phone.trim().isEmpty()) {
            phone = existing.getPhone();
        }

        System.out.print(
                "Email [" +
                        existing.getEmail() +
                        "]: "
        );

        String email =
                scanner.nextLine();

        if (email.trim().isEmpty()) {
            email = existing.getEmail();
        }

        System.out.print(
                "Address [" +
                        existing.getAddress() +
                        "]: "
        );

        String address =
                scanner.nextLine();

        if (address.trim().isEmpty()) {
            address = existing.getAddress();
        }

        Customer updated =
                new Customer(
                        customerId,
                        name,
                        phone,
                        email,
                        address
                );

        boolean success =
                customerService.updateCustomer(
                        updated
                );

        System.out.println(
                success
                        ? "Customer updated successfully."
                        : "Unable to update customer."
        );
    }

    private void createOrder() {

        System.out.println(
                "\n========== CREATE MULTI-PRODUCT ORDER =========="
        );

        viewCustomers();

        System.out.print(
                "\nEnter Customer ID: "
        );

        int customerId =
                readInt();

        Customer customer =
                customerService.getAllCustomers()
                        .stream()
                        .filter(c ->
                                c.getCustomerId()
                                        == customerId)
                        .findFirst()
                        .orElse(null);

        if (customer == null) {

            System.out.println(
                    "Customer not found."
            );

            return;
        }

        List<Integer> productIds =
                new ArrayList<>();

        List<Integer> quantities =
                new ArrayList<>();

        List<Double> unitPrices =
                new ArrayList<>();

        List<Double> subtotals =
                new ArrayList<>();

        double total = 0;

        while (true) {

            viewAllProducts();

            System.out.print(
                    "\nEnter Product ID: "
            );

            int productId =
                    readInt();

            Product product =
                    productService.getAllProducts()
                            .stream()
                            .filter(p ->
                                    p.getProductId()
                                            == productId)
                            .findFirst()
                            .orElse(null);

            if (product == null) {

                System.out.println(
                        "Product not found."
                );

                continue;
            }

            System.out.print(
                    "Quantity: "
            );

            int quantity =
                    readInt();

            if (quantity <= 0) {

                System.out.println(
                        "Quantity must be greater than zero."
                );

                continue;
            }

            if (quantity >
                    product.getStockQuantity()) {

                System.out.println(
                        "Insufficient stock. Available: " +
                                product.getStockQuantity()
                );

                continue;
            }

            double subtotal =
                    product.getUnitPrice()
                            * quantity;

            productIds.add(productId);
            quantities.add(quantity);
            unitPrices.add(product.getUnitPrice());
            subtotals.add(subtotal);

            total += subtotal;

            System.out.printf(
                    "Added: %s × %d = ₹%.2f%n",
                    product.getProductName(),
                    quantity,
                    subtotal
            );

            System.out.print(
                    "Add another product? (Y/N): "
            );

            String another =
                    scanner.nextLine();

            if (!another.equalsIgnoreCase("Y")) {
                break;
            }
        }

        if (productIds.isEmpty()) {

            System.out.println(
                    "No products were added."
            );

            return;
        }

        System.out.println(
                "\nOrder Total: ₹" + total
        );

        System.out.print(
                "Create this order? (Y/N): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Order creation cancelled."
            );

            return;
        }

        Order order =
                new Order(
                        0,
                        customerId,
                        loggedInUser.getUserId(),
                        null,
                        total,
                        "PENDING"
                );

        int orderId =
                orderService.createOrder(order);

        if (orderId == -1) {

            System.out.println(
                    "Unable to create order."
            );

            return;
        }

        for (int i = 0;
             i < productIds.size();
             i++) {

            OrderItem item =
                    new OrderItem(
                            0,
                            orderId,
                            productIds.get(i),
                            quantities.get(i),
                            unitPrices.get(i),
                            subtotals.get(i)
                    );

            boolean success =
                    orderService
                            .addOrderItemAndUpdateStock(
                                    item,
                                    loggedInUser.getUserId()
                            );

            if (!success) {

                System.out.println(
                        "Warning: Product " +
                                productIds.get(i) +
                                " could not be processed."
                );

                return;
            }
        }

        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "      ORDER CREATED SUCCESSFULLY"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Order ID: " + orderId
        );

        System.out.println(
                "Customer: " +
                        customer.getCustomerName()
        );

        System.out.printf(
                "Total Amount: ₹%.2f%n",
                total
        );

        System.out.println(
                "Products: " +
                        productIds.size()
        );

        System.out.println(
                "Status: PENDING"
        );

        System.out.println(
                "Inventory updated."
        );
    }

    private void viewOrders() {

        System.out.println(
                "\n---------- ORDERS ----------"
        );

        List<Order> orders =
                orderService.getAllOrders();

        if (orders.isEmpty()) {

            System.out.println(
                    "No orders found."
            );

            return;
        }

        System.out.printf(
                "%-8s %-12s %-10s %-15s %-15s%n",
                "Order ID",
                "Customer ID",
                "User ID",
                "Total",
                "Status"
        );

        System.out.println(
                "----------------------------------------------------------"
        );

        for (Order order : orders) {

            System.out.printf(
                    "%-8d %-12d %-10d ₹%-14.2f %-15s%n",
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getUserId(),
                    order.getTotalAmount(),
                    order.getStatus()
            );
        }
    }

    private void confirmOrder() {

        viewOrders();

        System.out.print(
                "\nEnter Order ID to confirm: "
        );

        int orderId =
                readInt();

        boolean success =
                orderService.confirmOrder(
                        orderId
                );

        System.out.println(
                success
                        ? "Order confirmed successfully."
                        : "Unable to confirm order."
        );
    }

    private void completeOrder() {

        viewOrders();

        System.out.print(
                "\nEnter Order ID to complete: "
        );

        int orderId =
                readInt();

        boolean success =
                orderService.completeOrder(
                        orderId
                );

        System.out.println(
                success
                        ? "Order completed successfully."
                        : "Unable to complete order. "
                        + "Only CONFIRMED orders can be completed."
        );
    }

    private void cancelOrder() {

        viewOrders();

        System.out.print(
                "\nEnter Order ID to cancel: "
        );

        int orderId =
                readInt();

        System.out.print(
                "Are you sure? (Y/N): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Cancellation cancelled."
            );

            return;
        }

        boolean success =
                orderService.cancelOrder(
                        orderId,
                        loggedInUser.getUserId()
                );

        System.out.println(
                success
                        ? "Order cancelled and stock restored."
                        : "Unable to cancel order."
        );
    }

    private void receiveStock() {

        viewAllProducts();

        System.out.print(
                "\nEnter Product ID: "
        );

        int productId =
                readInt();

        Product product =
                productService.getAllProducts()
                        .stream()
                        .filter(p ->
                                p.getProductId()
                                        == productId)
                        .findFirst()
                        .orElse(null);

        if (product == null) {

            System.out.println(
                    "Product not found."
            );

            return;
        }

        System.out.print(
                "Quantity received: "
        );

        int quantity =
                readInt();

        if (quantity <= 0) {

            System.out.println(
                    "Quantity must be greater than zero."
            );

            return;
        }

        System.out.print(
                "Remarks: "
        );

        String remarks =
                scanner.nextLine();

        if (remarks.trim().isEmpty()) {
            remarks =
                    "Stock received from supplier";
        }

        boolean success =
                stockService.increaseStock(
                        productId,
                        quantity,
                        loggedInUser.getUserId(),
                        remarks
                );

        System.out.println(
                success
                        ? "Stock received successfully."
                        : "Unable to update stock."
        );
    }

    private void viewStockTransactions() {

        viewAllProducts();

        System.out.print(
                "\nEnter Product ID: "
        );

        int productId =
                readInt();

        List<StockTransaction> transactions =
                stockService.getProductTransactions(
                        productId
                );

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transactions found."
            );

            return;
        }

        System.out.printf(
                "%-8s %-15s %-10s %-30s%n",
                "ID",
                "Type",
                "Quantity",
                "Remarks"
        );

        System.out.println(
                "------------------------------------------------------------"
        );

        for (StockTransaction transaction :
                transactions) {

            System.out.printf(
                    "%-8d %-15s %-10d %-30s%n",
                    transaction.getTransactionId(),
                    transaction.getTransactionType(),
                    transaction.getQuantity(),
                    transaction.getRemarks()
            );
        }
    }

    private void showReorderRecommendations() {

        System.out.println(
                "\n========== REORDER RECOMMENDATIONS =========="
        );

        List<Product> products =
                productService.getLowStockProducts();

        if (products.isEmpty()) {

            System.out.println(
                    "No products require reordering."
            );

            return;
        }

        System.out.printf(
                "%-5s %-25s %-10s %-10s %-15s%n",
                "ID",
                "Product",
                "Current",
                "Minimum",
                "Recommended"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Product product : products) {

            int recommended =
                    productService
                            .getRecommendedReorderQuantity(
                                    product
                            );

            System.out.printf(
                    "%-5d %-25s %-10d %-10d %-15d%n",
                    product.getProductId(),
                    product.getProductName(),
                    product.getStockQuantity(),
                    product.getMinimumStock(),
                    recommended
            );
        }
    }

    private void showReports() {

        System.out.println(
                "\n======================================"
        );

        System.out.println(
                "       INVENTORY & SALES REPORT"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Total Orders       : " +
                        reportService.getTotalOrders()
        );

        System.out.println(
                "Pending Orders     : " +
                        reportService.getPendingOrders()
        );

        System.out.println(
                "Confirmed Orders   : " +
                        reportService.getConfirmedOrders()
        );

        System.out.println(
                "Completed Orders   : " +
                        reportService.getCompletedOrders()
        );

        System.out.println(
                "Cancelled Orders   : " +
                        reportService.getCancelledOrders()
        );

        System.out.printf(
                "Total Sales        : ₹%.2f%n",
                reportService.getTotalSales()
        );

        System.out.printf(
                "Inventory Value    : ₹%.2f%n",
                reportService.getInventoryValue()
        );

        System.out.println(
                "Low Stock Products : " +
                        reportService.getLowStockCount()
        );

        System.out.println(
                "Out of Stock       : " +
                        reportService.getOutOfStockCount()
        );

        System.out.println(
                "======================================"
        );
    }

    private int readInt() {

        while (true) {

            String input =
                    scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.print(
                        "Please enter a valid integer: "
                );
            }
        }
    }

    private double readDouble() {

        while (true) {

            String input =
                    scanner.nextLine().trim();

            try {

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }
}