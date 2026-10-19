package com.mrudul.tourandtravel.controller;


import com.mrudul.tourandtravel.dto.request.DriverLoginRequest;
import com.mrudul.tourandtravel.dto.request.TourGuideLoginRequest;
import com.mrudul.tourandtravel.dto.request.TourGuideRequest;
import com.mrudul.tourandtravel.entity.Driver;
import com.mrudul.tourandtravel.entity.TourGuide;
import com.mrudul.tourandtravel.repository.TourGuideRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/tour_guides")
public class TourGuideController {

    private final TourGuideRepository tourGuideRepository;

    public TourGuideController(TourGuideRepository tourGuideRepository) {

        this.tourGuideRepository = tourGuideRepository;
    }


    @PostMapping
    public ResponseEntity<?> addTourGuide(@RequestBody TourGuideRequest request) {

        TourGuide tourGuide = new TourGuide();

        tourGuide.setGuideName(request.getGuideName());
        tourGuide.setGuideEmail(request.getGuideEmail());
        tourGuide.setGuidePhone(request.getGuidePhone());
        tourGuide.setLanguage(request.getLanguage());
        tourGuide.setJoiningDate(request.getJoiningDate());
        tourGuide.setGuideAddress(request.getGuideAddress());
        tourGuide.setProfileImageUrl(request.getProfileImageUrl());

        // Generate access code
        int number = 100 + new Random().nextInt(900);

        String accessCode = request.getGuideName().replaceAll("\\s+", "") + number;

        tourGuide.setAccessCode(accessCode);

        // Default status
        tourGuide.setStatus("active");

        TourGuide savedGuide = tourGuideRepository.save(tourGuide);

        return ResponseEntity.ok(savedGuide);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginTourGuide(
            @RequestBody TourGuideLoginRequest request) {

        TourGuide tourGuide = tourGuideRepository
                .findByGuideEmail(request.getTourGuideEmail())
                .orElse(null);

        // Email not found
        if (tourGuide == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or access code");
        }

        // Access code incorrect
        if (!tourGuide.getAccessCode().equals(request.getAccessCode())) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or access code");
        }

        // Driver is not active
        if (!"active".equalsIgnoreCase(tourGuide.getStatus())) {
            return ResponseEntity
                    .badRequest()
                    .body("Driver account is not active");
        }

        return ResponseEntity.ok(tourGuide);
    }


    @GetMapping
    public List<TourGuide> getAllTourGuides() {

        return tourGuideRepository.findAll();
    }


    @GetMapping("/{guideId}")
    public ResponseEntity<?> getTourGuide(
            @PathVariable Integer guideId) {

        TourGuide tourGuide = tourGuideRepository
                .findById(guideId)
                .orElse(null);

        if (tourGuide == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(tourGuide);
    }
}
