package ru.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private String id;
    private String customerId;
    private String repairmanId;
    private String repairmanName;
    private double price;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String description;
    private String updatedBy;

    public static final String CREATED = "CREATED";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String COMPLETED = "COMPLETED";
    public static final String CANCELLED = "CANCELLED";
}