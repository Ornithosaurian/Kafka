package com.example.kafka.repository;

import com.example.kafka.model.UserVisit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserVisitRepository extends MongoRepository<UserVisit, String> {
}
