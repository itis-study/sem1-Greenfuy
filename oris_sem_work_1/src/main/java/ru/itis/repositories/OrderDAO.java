package ru.itis.repositories;

import ru.itis.entities.Order;
import ru.itis.utils.ConnectionContainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.itis.servlets.base.BaseServlet.REPAIRMEN;

public class OrderDAO {

    private static final OrderDAO INSTANCE = new OrderDAO();
    private static final Connection connection = ConnectionContainer.getConnection();
    private static final String SELECT_FROM_ORDERS_WHERE_ID = "SELECT * FROM orders WHERE id = ?::uuid";
    private static final String SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID = "SELECT * FROM orders WHERE customer_id = ?::uuid LIMIT 10 OFFSET ?";
    private static final String SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID = "SELECT * FROM orders WHERE repairman_id = ?::uuid LIMIT 10 OFFSET ?";
    private static final String SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID_AND_STATUS = "SELECT * FROM orders WHERE customer_id = ?::uuid AND status = ? LIMIT 10 OFFSET ?";
    private static final String SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID_AND_STATUS = "SELECT * FROM orders WHERE repairman_id = ?::uuid AND status = ? LIMIT 10 OFFSET ?";
    private static final String UPDATE_ORDERS = "UPDATE orders SET status = ?, updated_at = ?, updated_by = ?::uuid WHERE id = ?::uuid";
    private static final String INSERT_INTO_ORDERS = "INSERT INTO orders (customer_id, repairman_id, repairman_name, price, status, created_at, updated_at, description, updated_by) VALUES (?::uuid, ?::uuid, ?, ?, ?, ?, ?, ?, ?::uuid)";
    private static final String SELECT_COUNT_FROM_ORDERS_WHERE_CUSTOMER_ID = "SELECT COUNT(*) FROM orders WHERE customer_id = ?::uuid";
    private static final String SELECT_COUNT_FROM_ORDERS_WHERE_REPAIRMAN_ID = "SELECT COUNT(*) FROM orders WHERE repairman_id = ?::uuid";

    public static final String ID = "id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String REPAIRMAN_ID = "repairman_id";
    public static final String USER_ID = "user_id";
    public static final String REPAIRMAN_NAME = "repairman_name";
    public static final String PRICE = "price";
    public static final String STATUS = "status";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DESCRIPTION = "description";
    public static final String DEFAULT = "default";

    public static OrderDAO getInstance() {
        return INSTANCE;
    }

    private OrderDAO() {
    }

    public Order getOrder(String id) throws SQLException {
        if (id == null) return null;
        PreparedStatement ps = connection.prepareStatement(SELECT_FROM_ORDERS_WHERE_ID);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        Order order = null;
        while (rs.next()) {
            order = new Order(
                    rs.getString(ID),
                    rs.getString(CUSTOMER_ID),
                    rs.getString(REPAIRMAN_ID),
                    rs.getString(REPAIRMAN_NAME),
                    rs.getDouble(PRICE),
                    rs.getString(STATUS),
                    rs.getTimestamp(CREATED_AT),
                    rs.getTimestamp(UPDATED_AT),
                    rs.getString(DESCRIPTION),
                    rs.getString(CUSTOMER_ID)
            );
        }
        return order;
    }

    public void updateOrder(Order order, String updatedBy) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_ORDERS);
        ps.setString(1, order.getStatus());
        ps.setTimestamp(2, order.getUpdatedAt());
        ps.setString(3, updatedBy);
        ps.setString(4, order.getId());
        ps.executeUpdate();
    }

    public void addOrder(Order order, String customerId, String repairmanId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_INTO_ORDERS);
        ps.setString(1, customerId);
        ps.setString(2, repairmanId);
        ps.setString(3, order.getRepairmanName());
        ps.setDouble(4, order.getPrice());
        ps.setString(5, order.getStatus());
        ps.setTimestamp(6, order.getCreatedAt());
        ps.setTimestamp(7, order.getUpdatedAt());
        ps.setString(8, order.getDescription());
        ps.setString(9, customerId);

        ps.executeUpdate();
    }

    public List<Order> getOrders(String orderBy, int offset, String userId, String role) throws SQLException {
        List<Order> orders = new ArrayList<>();
        System.out.println(orderBy);
        if (orderBy != null && offset >= 0) {
            PreparedStatement ps;
            if (orderBy.equals(Order.CREATED)) {
                ps = connection.prepareStatement(REPAIRMEN.equals(role) ?
                        SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID_AND_STATUS :
                        SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID_AND_STATUS);
                ps.setString(1, userId);
                ps.setString(2, Order.CREATED);
                ps.setInt(3, offset);
            } else if (orderBy.equals(Order.IN_PROGRESS)) {
                ps = connection.prepareStatement(REPAIRMEN.equals(role) ?
                        SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID_AND_STATUS :
                        SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID_AND_STATUS);
                ps.setString(1, userId);
                ps.setString(2, Order.IN_PROGRESS);
                ps.setInt(3, offset);
            } else if (orderBy.equals(Order.COMPLETED)) {
                ps = connection.prepareStatement(REPAIRMEN.equals(role) ?
                        SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID_AND_STATUS :
                        SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID_AND_STATUS);
                ps.setString(1, userId);
                ps.setString(2, Order.COMPLETED);
                ps.setInt(3, offset);
            } else if (orderBy.equals(Order.CANCELLED)) {
                ps = connection.prepareStatement(REPAIRMEN.equals(role) ?
                        SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID_AND_STATUS :
                        SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID_AND_STATUS);
                ps.setString(1, userId);
                ps.setString(2, Order.CANCELLED);
                ps.setInt(3, offset);
            } else {
                ps = connection.prepareStatement(REPAIRMEN.equals(role) ?
                        SELECT_FROM_ORDERS_WHERE_REPAIRMAN_ID :
                        SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID);
                ps.setString(1, userId);
                ps.setInt(2, offset);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString(ID),
                        rs.getString(CUSTOMER_ID),
                        rs.getString(REPAIRMAN_ID),
                        rs.getString(REPAIRMAN_NAME),
                        rs.getDouble(PRICE),
                        rs.getString(STATUS),
                        rs.getTimestamp(CREATED_AT),
                        rs.getTimestamp(UPDATED_AT),
                        rs.getString(DESCRIPTION),
                        rs.getString(CUSTOMER_ID)
                ));
            }
        }
        return orders;
    }

    public int getOrdersCount(String userId, String role) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                REPAIRMEN.equals(role) ?
                        SELECT_COUNT_FROM_ORDERS_WHERE_REPAIRMAN_ID :
                        SELECT_COUNT_FROM_ORDERS_WHERE_CUSTOMER_ID
        );
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
