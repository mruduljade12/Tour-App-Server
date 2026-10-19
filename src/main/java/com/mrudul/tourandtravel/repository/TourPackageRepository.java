package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourPackageRepository extends JpaRepository<TourPackage,Integer> {

    @Query("""
    SELECT DISTINCT tp
    FROM TourPackage tp
    LEFT JOIN TourPackageDestination tpd
        ON tpd.tourPackage.packageId = tp.packageId
    LEFT JOIN Destination d
        ON tpd.destination.destinationId = d.destinationId
    WHERE LOWER(tp.packName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(d.city) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(d.state) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<TourPackage> searchPackages(@Param("keyword") String keyword);
}
