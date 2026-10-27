
package com.mrudul.tourandtravel.controller;

import com.mrudul.tourandtravel.dto.request.TourPackageRequest;
import com.mrudul.tourandtravel.dto.response.TourPackageResponse;
import com.mrudul.tourandtravel.entity.TourPackage;
import com.mrudul.tourandtravel.service.TourPackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class TourPackageController {

    private final TourPackageService tourPackageService;

    public TourPackageController(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }

    @PostMapping
    public ResponseEntity<TourPackage> createPackage(
            @RequestBody TourPackageRequest request) {

        TourPackage savedPackage =
                tourPackageService.createPackage(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPackage);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<TourPackageResponse> getPackageById(
            @PathVariable Integer packageId) {

        TourPackageResponse response =
                tourPackageService.getPackageById(packageId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TourPackageResponse>> getAllPackages() {

        List<TourPackageResponse> responses =
                tourPackageService.getAllPackages();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/keyword")
    public ResponseEntity<List<TourPackageResponse>> searchPackagesByKeyword(
            @RequestParam String keyword) {

        List<TourPackageResponse> responses =
                tourPackageService.searchPackagesByKeyword(keyword);

        return ResponseEntity.ok(responses);
    }
}
