package ru.itis.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repairman {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private double ratingSum;
    private int finishedOrdersCount;

}
