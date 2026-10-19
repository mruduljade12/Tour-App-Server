package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("""
        SELECT COALESCE(SUM(b.numberOfPeople), 0)
        FROM Booking b
        WHERE b.tourPackage.packageId = :packageId
        AND b.status IN ('pending', 'confirmed')
    """)
    Long getBookedPeople(@Param("packageId") Integer packageId);

    List<Booking> findByCustomerEmailOrderByBookingIdDesc(String customerEmail);
    List<Booking> findByStatusOrderByBookingIdDesc(String status);
}
