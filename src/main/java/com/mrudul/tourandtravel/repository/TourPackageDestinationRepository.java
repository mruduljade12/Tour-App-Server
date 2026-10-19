package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.TourPackageDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourPackageDestinationRepository extends JpaRepository<TourPackageDestination,Integer> {

    List<TourPackageDestination>
    findByTourPackagePackageIdOrderByVisitOrderAsc(Integer packageId);
}
