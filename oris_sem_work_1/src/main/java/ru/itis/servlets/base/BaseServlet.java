package ru.itis.servlets.base;


import ru.itis.entities.Customer;
import ru.itis.entities.Order;
import ru.itis.entities.Repairman;
import ru.itis.entities.Review;
import ru.itis.repositories.CustomerDAO;
import ru.itis.repositories.OrderDAO;
import ru.itis.repositories.RepairmanDAO;
import ru.itis.repositories.ReviewDAO;

import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseServlet extends HttpServlet {

    private static final CustomerDAO customerDAO = CustomerDAO.getInstance();
    private static final RepairmanDAO repairmanDAO = RepairmanDAO.getInstance();
    private static final ReviewDAO reviewDAO = ReviewDAO.getInstance();
    private static final OrderDAO orderDAO = OrderDAO.getInstance();

    public static final String CUSTOMERS = "customers";
    public static final String REPAIRMEN = "repairmen";
    public static final String ADMIN = "admin";

    protected static Customer getCustomerById(String userId) throws SQLException {
        return customerDAO.getCustomer(CustomerDAO.ID, userId);
    }

    protected static Repairman getRepairmanById(String userId) throws SQLException {
        return repairmanDAO.getRepairman(RepairmanDAO.ID, userId);
    }

    protected static Review getReviewById(String reviewId) throws SQLException {
        return reviewDAO.getReview(reviewId);
    }

    protected static Order getOrderById(String orderId) throws SQLException {
        return orderDAO.getOrder(orderId);
    }

    protected static Customer getCustomerByEmail(String email) throws SQLException {
        return customerDAO.getCustomer(CustomerDAO.EMAIL, email);
    }

    protected static Repairman getRepairmanByEmail(String email) throws SQLException {
        return repairmanDAO.getRepairman(RepairmanDAO.EMAIL, email);
    }

    protected static void updateCustomer(Customer customer, String customerId) throws SQLException {
        customerDAO.updateCustomer(customer, customerId);
    }

    protected static void updateRepairman(Repairman repairman, String repairmanId) throws SQLException {
        repairmanDAO.updateRepairman(repairman, repairmanId);
    }


    protected static void addCustomer(Customer customer, String passwordHash) throws SQLException {
        customerDAO.addCustomer(customer, passwordHash);
    }

    protected static void addRepairman(Repairman repairman, String passwordHash) throws SQLException {
        repairmanDAO.addRepairman(repairman, passwordHash);
    }

    protected static void addReview(Review review) throws SQLException {
        reviewDAO.addReview(review);
    }

    protected static void addOrder(Order order, String customerId, String repairmanId) throws SQLException {
        orderDAO.addOrder(order, customerId, repairmanId);
    }

    protected static List<Repairman> getRepairmen(String sortBy, int pageNumber) throws SQLException {
        return repairmanDAO.getRepairmen(sortBy, pageNumber * 10);
    }

    protected static List<Order> getOrders(String show, int pageNumber, String userId, String role) throws SQLException {
        return orderDAO.getOrders(show, pageNumber * 10, userId, role);
    }

    protected static List<Review> getReviewsByRepairmanId(String repairmanId, int pageNumber) throws SQLException {
        return reviewDAO.getReviews(repairmanId,pageNumber * 10);
    }

    protected static int getRepairmenCount() throws SQLException {
        return repairmanDAO.getRepairmenCount();
    }

    protected static int getReviewsCount(String repairmanId) throws SQLException {
        return reviewDAO.getReviewsCount(repairmanId);
    }

    protected static int getOrdersCount(String userId, String role) throws SQLException {
        return orderDAO.getOrdersCount(userId, role);
    }

    protected static boolean validatePassword(String role, String email, String passwordHash) throws SQLException {
        if (role.equals(REPAIRMEN)) {
            Repairman repairman = repairmanDAO.getRepairmanByEmailAndPasswordHash(email, passwordHash);
            return repairman != null;
        }
        if (role.equals(CUSTOMERS)) {
            Customer customer = customerDAO.getCustomerByEmailAndPasswordHash(email, passwordHash);
            return customer != null;
        }
        return false;

    }

    protected static boolean isEmailUnique(String email) throws SQLException {
        Customer customer = getCustomerByEmail(email);
        if (customer == null) {
            Repairman repairman = getRepairmanByEmail(email);
            return repairman == null;
        }
        return false;
    }
 }
