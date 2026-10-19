package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourGuideRepository extends JpaRepository<TourGuide,Integer> {
    List<TourGuide> findByStatus(String status);
    Optional<TourGuide> findByGuideEmail(String email);
}
