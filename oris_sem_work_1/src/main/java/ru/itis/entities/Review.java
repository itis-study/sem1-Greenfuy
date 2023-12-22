package ru.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private String id;
    private String repairmanId;
    private String customerId;
    private String customerName;
    private String text;
    private String date;
    private double rating;

}
