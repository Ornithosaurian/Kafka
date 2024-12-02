package com.example.kafka.repository;

import com.example.kafka.model.UserSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSummaryRepository extends MongoRepository<UserSummary, String> {
    UserSummary findByUserId(String userId);
}
