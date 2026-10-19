package com.mrudul.tourandtravel.controller;


import com.mrudul.tourandtravel.dto.request.DriverLoginRequest;
import com.mrudul.tourandtravel.dto.request.DriverRequest;
import com.mrudul.tourandtravel.entity.Driver;
import com.mrudul.tourandtravel.repository.DriverRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @PostMapping
    public ResponseEntity<?> addDriver(@RequestBody DriverRequest request) {

        Driver driver = new Driver();

        driver.setDriverName(request.getDriverName());
        driver.setDriverEmail(request.getDriverEmail());
        driver.setDriverPhone(request.getDriverPhone());
        driver.setLicenseNumber(request.getLicenseNumber());
        driver.setJoiningDate(request.getJoiningDate());
        driver.setDriverAddress(request.getDriverAddress());
        driver.setProfileImageUrl(request.getProfileImageUrl());

        // Generate access code
        int number = 100 + new Random().nextInt(900);

        String accessCode =
                request.getDriverName().replaceAll("\\s+", "") + number;

        driver.setAccessCode(accessCode);

        // Default status
        driver.setStatus("active");

        Driver savedDriver = driverRepository.save(driver);

        return ResponseEntity.ok(savedDriver);
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginDriver(
            @RequestBody DriverLoginRequest request) {

        Driver driver = driverRepository
                .findByDriverEmail(request.getDriverEmail())
                .orElse(null);

        // Email not found
        if (driver == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or access code");
        }

        // Access code incorrect
        if (!driver.getAccessCode().equals(request.getAccessCode())) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or access code");
        }

        // Driver is not active
        if (!"active".equalsIgnoreCase(driver.getStatus())) {
            return ResponseEntity
                    .badRequest()
                    .body("Driver account is not active");
        }

        return ResponseEntity.ok(driver);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<?> getDriver(
            @PathVariable Integer driverId) {

        Driver driver = driverRepository
                .findById(driverId)
                .orElse(null);

        if (driver == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(driver);
    }
}
