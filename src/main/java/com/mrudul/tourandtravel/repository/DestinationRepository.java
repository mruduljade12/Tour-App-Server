package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination,Integer> {
    List<Destination> findByStatus(String status);
}
