package com.example.kafka.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "user_summary")
public class UserSummary {
    @Id
    private String id;
    private String userId;
    private int loginAmount;
    private Instant firstJoin;
}
