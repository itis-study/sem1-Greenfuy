package ru.itis.repositories;

import ru.itis.entities.Review;
import ru.itis.utils.ConnectionContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    private static final ReviewDAO INSTANCE = new ReviewDAO();
    private static final Connection connection = ConnectionContainer.getConnection();
    private static final String SELECT_FROM_REVIEWS = "SELECT (customer_name, text, date, rating) FROM reviews";
    private static final String SELECT_FROM_REVIEWS_WHERE_ID = "SELECT * FROM reviews WHERE id = ?::uuid";
    private static final String SELECT_FROM_REVIEWS_WHERE_REPAIRMAN_ID = "SELECT * FROM reviews WHERE repairman_id = ?::uuid LIMIT 10 OFFSET ?";
    private static final String SELECT_COUNT_FROM_REVIEWS_WHERE_REPAIRMAN_ID = "SELECT COUNT(*) FROM reviews WHERE repairman_id = ?::uuid";
    private static final String UPDATE_REVIEWS = "UPDATE reviews SET customer_name = ?, text = ?, date = ?, rating = ? WHERE id = ?::uuid";
    private static final String INSERT_INTO_REVIEWS = "INSERT INTO reviews (repairman_id, customer_id, customer_name, text, date, rating) VALUES (?::uuid, ?::uuid, ?, ?, ?, ?)";

    public static final String ID = "id";
    public static final String REPAIRMAN_ID = "repairman_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String TEXT = "text";
    public static final String DATE = "date";
    public static final String RATING = "rating";

    public static ReviewDAO getInstance() {
        return INSTANCE;
    }

    private ReviewDAO() {
    }

    public Review getReview(String id) throws SQLException {
        if (id == null) return null;
        PreparedStatement ps = connection.prepareStatement(SELECT_FROM_REVIEWS_WHERE_ID);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        Review review = null;
        while (rs.next()) {
            review = new Review(
                    id,
                    rs.getString(REPAIRMAN_ID),
                    rs.getString(CUSTOMER_ID),
                    rs.getString(CUSTOMER_NAME),
                    rs.getString(TEXT),
                    rs.getString(DATE),
                    rs.getDouble(RATING)
            );
        }
        return review;
    }

    public void updateReview(Review review) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(UPDATE_REVIEWS);
        ps.setString(1, review.getRepairmanId());
        ps.setString(2, review.getCustomerId());
        ps.setString(3, review.getCustomerName());
        ps.setString(4, review.getText());
        ps.setString(5, review.getDate());
        ps.setDouble(6, review.getRating());
        ps.setString(7, review.getId());
        ps.executeUpdate();
    }

    public void addReview(Review review) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(INSERT_INTO_REVIEWS);
        ps.setString(1, review.getRepairmanId());
        ps.setString(2, review.getCustomerId());
        ps.setString(3, review.getCustomerName());
        ps.setString(4, review.getText());
        ps.setString(5, review.getDate());
        ps.setDouble(6, review.getRating());
        ps.executeUpdate();
    }

    public List<Review> getReviews(String repairmanId, int offset) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_FROM_REVIEWS_WHERE_REPAIRMAN_ID);
        ps.setString(1, repairmanId);
        ps.setInt(2, offset);
        ResultSet rs = ps.executeQuery();
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            reviews.add(Review.builder()
                    .customerName(rs.getString(CUSTOMER_NAME))
                    .text(rs.getString(TEXT))
                    .date(rs.getString(DATE))
                    .rating(rs.getDouble(RATING))
                    .build()
            );
        }
        return reviews;
    }

    public int getReviewsCount(String repairmanId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SELECT_COUNT_FROM_REVIEWS_WHERE_REPAIRMAN_ID);
        ps.setString(1, repairmanId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
