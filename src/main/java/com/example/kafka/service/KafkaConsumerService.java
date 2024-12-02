package com.example.kafka.service;

import com.example.kafka.model.CarView;
import com.example.kafka.model.UserSummary;
import com.example.kafka.model.UserVisit;
import com.example.kafka.repository.CarViewRepository;
import com.example.kafka.repository.UserSummaryRepository;
import com.example.kafka.repository.UserVisitRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class KafkaConsumerService {

    private final CarViewRepository carViewRepository;
    private final UserVisitRepository userVisitRepository;
    private final UserSummaryRepository userSummaryRepository;

    public KafkaConsumerService(CarViewRepository carViewRepository,
                                UserVisitRepository userVisitRepository,
                                UserSummaryRepository userSummaryRepository) {
        this.carViewRepository = carViewRepository;
        this.userVisitRepository = userVisitRepository;
        this.userSummaryRepository = userSummaryRepository;
    }

    @KafkaListener(topics = "car_views", groupId = "car-logging-group")
    public void listenCarViews(ConsumerRecord<String, String> record) {
        try {
            String carId = record.value();

            CarView carView = carViewRepository.findById(carId).orElse(new CarView());
            carView.setCarId(carId);
            carView.setWatchAmount(carView.getWatchAmount() + 1);
            carViewRepository.save(carView);

            System.out.println("Car view updated: " + carId);
        } catch (Exception e) {
            System.err.println("Error processing car view: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "user_activity", groupId = "car-logging-group")
    public void listenUserActivity(ConsumerRecord<String, String> record) {
        try {
            String[] parts = record.value().split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid message format for user_activity");
            }

            String userId = parts[0];
            Instant timestamp = Instant.parse(parts[1]);

            UserVisit userVisit = new UserVisit();
            userVisit.setUserId(userId);
            userVisit.setTimestamp(timestamp);
            userVisitRepository.save(userVisit);


            UserSummary userSummary = userSummaryRepository.findByUserId(userId);
            if (userSummary == null) {
                userSummary = new UserSummary();
                userSummary.setUserId(userId);
                userSummary.setLoginAmount(1);
                userSummary.setFirstJoin(timestamp);
            } else {
                userSummary.setLoginAmount(userSummary.getLoginAmount() + 1);
                if (userSummary.getFirstJoin() == null || timestamp.isBefore(userSummary.getFirstJoin())) {
                    userSummary.setFirstJoin(timestamp);
                }
            }
            userSummaryRepository.save(userSummary);

            System.out.println("User activity updated: " + userId);
        } catch (Exception e) {
            System.err.println("Error processing user activity: " + e.getMessage());
        }
    }
}