package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveApplication, Integer> {

    List<LeaveApplication> findByDriverId(Integer driverId);

    List<LeaveApplication> findByGuideId(Integer guideId);

    List<LeaveApplication> findByStatus(String status);

    boolean existsByDriverIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer driverId,
            String status,
            LocalDate endDate,
            LocalDate startDate
    );

    boolean existsByGuideIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer guideId,
            String status,
            LocalDate endDate,
            LocalDate startDate
    );

    @Query("""
    SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
    FROM LeaveApplication l
    WHERE l.driverId = :driverId
    AND l.status = 'APPROVED'
    AND l.startDate <= :date
    AND l.endDate >= :date
""")
    boolean isDriverOnLeave(
            @Param("driverId") Integer driverId,
            @Param("date") LocalDate date
    );

    @Query("""
    SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END
    FROM LeaveApplication l
    WHERE l.guideId = :guideId
    AND l.status = 'APPROVED'
    AND l.startDate <= :date
    AND l.endDate >= :date
""")
    boolean isGuideOnLeave(
            @Param("guideId") Integer guideId,
            @Param("date") LocalDate date
    );
}