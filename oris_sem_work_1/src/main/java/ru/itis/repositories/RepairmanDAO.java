package ru.itis.repositories;

import ru.itis.entities.Repairman;
import ru.itis.utils.ConnectionContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairmanDAO {

    private static final RepairmanDAO INSTANCE = new RepairmanDAO();
    private static final Connection connection = ConnectionContainer.getConnection();
    private static final String SELECT_FROM_REPAIRMEN_WHERE_EMAIL = "SELECT * FROM repairmen WHERE email = ?";
    private static final String SELECT_FROM_REPAIRMEN_WHERE_EMAIL_AND_PASSWORD_HASH = "SELECT * FROM repairmen WHERE email = ? AND password_hash = ?";
    private static final String SELECT_FROM_REPAIRMEN_WHERE_ID = "SELECT * FROM repairmen WHERE id = ?::uuid";
    private static final String SELECT_FROM_REPAIRMEN = "SELECT * FROM repairmen LIMIT 10 OFFSET ?";
    private static final String SELECT_FROM_REPAIRMEN_ORDER_BY = "SELECT * FROM repairmen ORDER BY ? DESC LIMIT 10 OFFSET ?";
    private static final String SELECT_FROM_REPAIRMEN_ORDER_BY_RATING_SUM = "SELECT * FROM repairmen ORDER BY rating_sum DESC LIMIT 10 OFFSET ?";
    private static final String SELECT_FROM_REPAIRMEN_ORDER_BY_FINISHED_ORDERS_COUNT = "SELECT * FROM repairmen ORDER BY finished_orders_count DESC LIMIT 10 OFFSET ?";
    private static final String UPDATE_REPAIRMEN = "UPDATE repairmen SET first_name = ?, last_name = ?, email = ?, description = ?, rating_sum = ?, finished_orders_count = ? WHERE id = ?::uuid";
    private static final String INSERT_INTO_REPAIRMEN = "INSERT INTO repairmen (first_name, last_name, email, password_hash, description, rating_sum, finished_orders_count) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_COUNT_FROM_REPAIRMEN = "SELECT COUNT(*) FROM repairmen";

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String DESCRIPTION = "description";
    public static final String RATING_SUM = "rating_sum";
    public static final String FINISHED_ORDERS_COUNT = "finished_orders_count";
    public static final String DEFAULT = "default";

    public static RepairmanDAO getInstance() {
        return INSTANCE;
    }

    private RepairmanDAO() {
    }

    public Repairman getRepairman(String paramName, String paramValue) throws SQLException {
        if (paramName == null || paramValue == null) {
            return null;
        }
        PreparedStatement ps;
        if (paramName.equals(EMAIL)) {
            ps = connection.prepareStatement(SELECT_FROM_REPAIRMEN_WHERE_EMAIL);
            ps.setString(1, paramValue);
        } else {
            ps = connection.prepareStatement(SELECT_FROM_REPAIRMEN_WHERE_ID);
            ps.setString(1, paramValue);
        }
        ResultSet rs = ps.executeQuery();
        Repairman repairman = null;
        while (rs.next()) {
            repairman = new Repairman(
                    rs.getString(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(EMAIL),
                    rs.getString(DESCRIPTION),
                    rs.getDouble(RATING_SUM),
                    rs.getInt(FINISHED_ORDERS_COUNT)
            );
        }
        return repairman;
    }

    public Repairman getRepairmanByEmailAndPasswordHash(String email, String passwordHash) throws SQLException {
        if (email == null || passwordHash == null) {
            return null;
        }
        PreparedStatement ps = connection.prepareStatement(SELECT_FROM_REPAIRMEN_WHERE_EMAIL_AND_PASSWORD_HASH);
        ps.setString(1, email);
        ps.setString(2, passwordHash);
        ResultSet rs = ps.executeQuery();
        Repairman repairman = null;
        while (rs.next()) {
            repairman = new Repairman(
                    rs.getString(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(EMAIL),
                    rs.getString(DESCRIPTION),
                    rs.getDouble(RATING_SUM),
                    rs.getInt(FINISHED_ORDERS_COUNT)
            );
        }
        return repairman;
    }

    public void updateRepairman(Repairman repairman, String repairmanId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_REPAIRMEN);
        ps.setString(1, repairman.getFirstName());
        ps.setString(2, repairman.getLastName());
        ps.setString(3, repairman.getEmail());
        ps.setString(4, repairman.getDescription());
        ps.setDouble(5, repairman.getRatingSum());
        ps.setInt(6, repairman.getFinishedOrdersCount());
        ps.setString(7, repairmanId);
        ps.executeUpdate();
    }

    public void addRepairman(Repairman repairman, String passwordHash) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_INTO_REPAIRMEN);
        ps.setString(1, repairman.getFirstName());
        ps.setString(2, repairman.getLastName());
        ps.setString(3, repairman.getEmail());
        ps.setString(4, passwordHash);
        ps.setString(5, repairman.getDescription());
        ps.setDouble(6, repairman.getRatingSum());
        ps.setInt(7, repairman.getFinishedOrdersCount());
        ps.executeUpdate();
    }


    public List<Repairman> getRepairmen(String orderBy, int offset) throws SQLException {
        List<Repairman> repairmen = new ArrayList<>();
        if (orderBy != null && offset >= 0) {
            PreparedStatement ps;
            if (orderBy.equals(RATING_SUM)) {
                ps = connection.prepareStatement(SELECT_FROM_REPAIRMEN_ORDER_BY_RATING_SUM);
                ps.setInt(1, offset);
            } else if (orderBy.equals(FINISHED_ORDERS_COUNT)) {
                ps = connection.prepareStatement(SELECT_FROM_REPAIRMEN_ORDER_BY_FINISHED_ORDERS_COUNT);
                ps.setInt(1, offset);
            } else {
                ps = connection.prepareStatement(SELECT_FROM_REPAIRMEN);
                ps.setInt(1, offset);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                repairmen.add(new Repairman(
                        rs.getString(ID),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME),
                        rs.getString(EMAIL),
                        rs.getString(DESCRIPTION),
                        rs.getDouble(RATING_SUM),
                        rs.getInt(FINISHED_ORDERS_COUNT)
                ));
            }
        }
        return repairmen;
    }

    public int getRepairmenCount() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_COUNT_FROM_REPAIRMEN);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
