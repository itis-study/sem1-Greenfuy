package ru.itis.repositories;

import ru.itis.entities.Customer;
import ru.itis.utils.ConnectionContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final CustomerDAO INSTANCE = new CustomerDAO();
    private static final Connection connection = ConnectionContainer.getConnection();
    private static final String SELECT_FROM_CUSTOMERS = "SELECT * FROM customers";
    private static final String SELECT_FROM_CUSTOMERS_WHERE_EMAIL = "SELECT * FROM customers WHERE email = ?";
    private static final String SELECT_FROM_CUSTOMERS_WHERE_EMAIL_AND_PASSWORD_HASH = "SELECT * FROM customers WHERE email = ? AND password_hash = ?";
    private static final String SELECT_FROM_CUSTOMERS_WHERE_ID = "SELECT * FROM customers WHERE id = ?::uuid";
    private static final String UPDATE_CUSTOMERS = "UPDATE customers SET first_name = ?, last_name = ?, email = ? WHERE id = ?::uuid";
    private static final String INSERT_INTO_CUSTOMERS = "INSERT INTO customers (first_name, last_name, email, password_hash) VALUES (?, ?, ?, ?)";

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";


    public static CustomerDAO getInstance() {
        return INSTANCE;
    }

    private CustomerDAO() {
    }

    public Customer getCustomer(String paramName, String paramValue) throws SQLException {
        if (paramName == null || paramValue == null) {
            return null;
        }
        PreparedStatement ps;
        if (paramName.equals(EMAIL)) {
            ps = connection.prepareStatement(SELECT_FROM_CUSTOMERS_WHERE_EMAIL);
            ps.setString(1, paramValue);
        } else {
            ps = connection.prepareStatement(SELECT_FROM_CUSTOMERS_WHERE_ID);
            ps.setString(1, paramValue);
        }
        ResultSet rs = ps.executeQuery();
        Customer customer = null;
        while (rs.next()) {
            customer = new Customer(
                    rs.getString(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(EMAIL)
            );
        }
        return customer;
    }

    public Customer getCustomerByEmailAndPasswordHash(String email, String passwordHash) throws SQLException {
        if (email == null || passwordHash == null) {
            return null;
        }
        PreparedStatement ps = connection.prepareStatement(SELECT_FROM_CUSTOMERS_WHERE_EMAIL_AND_PASSWORD_HASH);
        ps.setString(1, email);
        ps.setString(2, passwordHash);
        ResultSet rs = ps.executeQuery();
        Customer customer = null;
        while (rs.next()) {
            customer = new Customer(
                    rs.getString(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(EMAIL)
            );
        }
        return customer;
    }

    public void updateCustomer(Customer customer, String customerId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_CUSTOMERS);
        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getLastName());
        ps.setString(3, customer.getEmail());
        ps.setString(4, customerId);
        ps.executeUpdate();
    }

    public void addCustomer(Customer customer, String passwordHash) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_INTO_CUSTOMERS);
        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getLastName());
        ps.setString(3, customer.getEmail());
        ps.setString(4, passwordHash);
        ps.executeUpdate();
    }

    public List<Customer> getCustomers() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_FROM_CUSTOMERS);
        ResultSet rs = ps.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(EMAIL)
            ));
        }
        return customers;
    }

}
