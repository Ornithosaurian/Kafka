package com.example.kafka.controller;

import com.example.kafka.model.CarView;
import com.example.kafka.model.UserSummary;
import com.example.kafka.model.UserVisit;
import com.example.kafka.repository.CarViewRepository;
import com.example.kafka.repository.UserSummaryRepository;
import com.example.kafka.repository.UserVisitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CarViewRepository carViewRepository;
    private final UserVisitRepository userVisitRepository;
    private final UserSummaryRepository userSummaryRepository;

    public AdminController(CarViewRepository carViewRepository,
                           UserVisitRepository userVisitRepository,
                           UserSummaryRepository userSummaryRepository) {
        this.carViewRepository = carViewRepository;
        this.userVisitRepository = userVisitRepository;
        this.userSummaryRepository = userSummaryRepository;
    }

    @GetMapping("/car-views")
    public List<CarView> getCarViews() {
        return carViewRepository.findAll();
    }


    @GetMapping("/user-visits")
    public List<UserVisit> getUserVisits() {
        return userVisitRepository.findAll();
    }

    @GetMapping("/user-summary")
    public List<UserSummary> getUserSummaries() {
        return userSummaryRepository.findAll();
    }

    @GetMapping("/car-view/{carId}")
    public CarView getCarViewByCarId(@PathVariable String carId) {
        return carViewRepository.findById(carId).orElse(null);
    }

    @GetMapping("/user-summary/{userId}")
    public UserSummary getUserSummaryByUserId(@PathVariable String userId) {
        return userSummaryRepository.findByUserId(userId);
    }
}