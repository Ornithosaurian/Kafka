package com.example.kafka.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "car_views")
public class CarView {
    @Id
    private String carId;
    private int watchAmount;
}