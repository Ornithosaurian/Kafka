package com.example.kafka.repository;

import com.example.kafka.model.CarView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarViewRepository extends MongoRepository<CarView, String> {
}
