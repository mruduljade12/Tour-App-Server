package com.mrudul.tourandtravel.service;

import com.mrudul.tourandtravel.dto.request.PackageDestinationRequest;
import com.mrudul.tourandtravel.dto.request.TourPackageRequest;
import com.mrudul.tourandtravel.dto.response.DriverResponse;
import com.mrudul.tourandtravel.dto.response.PackageDestinationResponse;
import com.mrudul.tourandtravel.dto.response.TourGuideResponse;
import com.mrudul.tourandtravel.dto.response.TourPackageResponse;
import com.mrudul.tourandtravel.entity.*;
import com.mrudul.tourandtravel.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;
    private final DestinationRepository destinationRepository;
    private final DriverRepository driverRepository;
    private final TourGuideRepository tourGuideRepository;
    private final TourPackageDestinationRepository tourPackageDestinationRepository;


    public TourPackageService(
            TourPackageRepository tourPackageRepository,
            DestinationRepository destinationRepository,
            DriverRepository driverRepository,
            TourGuideRepository tourGuideRepository,
            TourPackageDestinationRepository
                    tourPackageDestinationRepository) {

        this.tourPackageRepository = tourPackageRepository;
        this.destinationRepository = destinationRepository;
        this.driverRepository = driverRepository;
        this.tourGuideRepository = tourGuideRepository;
        this.tourPackageDestinationRepository = tourPackageDestinationRepository;
    }


    @Transactional
    public TourPackage createPackage(TourPackageRequest request) {

        // 1. Find Driver
        Driver driver = driverRepository
                .findById(request.getDriverId())
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));


        // 2. Find Tour Guide
        TourGuide guide = tourGuideRepository
                .findById(request.getGuideId())
                .orElseThrow(() ->
                        new RuntimeException("Tour guide not found"));


        // 3. Create Package
        TourPackage tourPackage = new TourPackage();

        tourPackage.setPackName(request.getPackName());
        tourPackage.setPackDescription(request.getPackDescription());
        tourPackage.setPackDurationDay(request.getPackDurationDay());
        tourPackage.setPackDurationNight(request.getPackDurationNight());
        tourPackage.setPackPrice(request.getPackPrice());
        tourPackage.setPackMaxCapacity(request.getPackMaxCapacity());
        tourPackage.setPackThumbnail(request.getPackThumbnail());
        tourPackage.setPackStatus(request.getPackStatus());
        tourPackage.setPackStartDate(request.getPackStartDate());
        tourPackage.setDriver(driver);
        tourPackage.setGuide(guide);


        // 4. Save Package
        TourPackage savedPackage = tourPackageRepository.save(tourPackage);


        // 5. Save Destinations
        for (PackageDestinationRequest destinationRequest : request.getDestinations()) {

            Destination destination =
                    destinationRepository
                            .findById(
                                    destinationRequest
                                            .getDestinationId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Destination not found"));


            TourPackageDestination packageDestination = new TourPackageDestination();

            packageDestination.setTourPackage(savedPackage);
            packageDestination.setDestination(destination);
            packageDestination.setDayNumber(destinationRequest.getDayNumber());
            packageDestination.setVisitOrder(destinationRequest.getVisitOrder());
            packageDestination.setVisitDate(destinationRequest.getVisitDate());

            tourPackageDestinationRepository.save(packageDestination);
        }


        return savedPackage;
    }


    public TourPackageResponse getPackageById(Integer packageId) {

        TourPackage tourPackage = tourPackageRepository
                .findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        TourPackageResponse response = new TourPackageResponse();

        // Package details
        response.setPackageId(tourPackage.getPackageId());
        response.setPackName(tourPackage.getPackName());
        response.setPackDescription(tourPackage.getPackDescription());
        response.setPackDurationDay(tourPackage.getPackDurationDay());
        response.setPackDurationNight(tourPackage.getPackDurationNight());
        response.setPackPrice(tourPackage.getPackPrice());
        response.setPackMaxCapacity(tourPackage.getPackMaxCapacity());
        response.setPackThumbnail(tourPackage.getPackThumbnail());
        response.setPackStatus(tourPackage.getPackStatus());
        response.setPackStartDate(tourPackage.getPackStartDate());
        response.setPackRating(tourPackage.getPackRating());


        // Driver
        Driver driver = tourPackage.getDriver();

        DriverResponse driverResponse = new DriverResponse();

        driverResponse.setDriverId(driver.getDriverId());
        driverResponse.setDriverName(driver.getDriverName());
        driverResponse.setDriverPhone(driver.getDriverPhone());
        driverResponse.setProfileImageUrl(driver.getProfileImageUrl());

        response.setDriver(driverResponse);


        // Tour Guide
        TourGuide guide = tourPackage.getGuide();

        TourGuideResponse guideResponse = new TourGuideResponse();

        guideResponse.setGuideId(guide.getGuideId());
        guideResponse.setGuideName(guide.getGuideName());
        guideResponse.setGuidePhone(guide.getGuidePhone());
        guideResponse.setLanguage(guide.getLanguage());
        guideResponse.setProfileImageUrl(guide.getProfileImageUrl());
        guideResponse.setJoiningDate(guide.getJoiningDate());

        response.setGuide(guideResponse);


        // Destinations
        List<TourPackageDestination> packageDestinations =
                tourPackageDestinationRepository
                        .findByTourPackagePackageIdOrderByVisitOrderAsc(packageId);

        List<PackageDestinationResponse> destinationResponses =
                new ArrayList<>();

        for (TourPackageDestination packageDestination : packageDestinations) {

            Destination destination = packageDestination.getDestination();

            PackageDestinationResponse destinationResponse =
                    new PackageDestinationResponse();

            destinationResponse.setDestinationId(
                    destination.getDestinationId());

            destinationResponse.setName(
                    destination.getName());

            destinationResponse.setCity(
                    destination.getCity());

            destinationResponse.setState(
                    destination.getState());

            destinationResponse.setCountry(
                    destination.getCountry());

            destinationResponse.setDescription(
                    destination.getDescription());

            destinationResponse.setLatitude(
                    destination.getLatitude());

            destinationResponse.setLongitude(
                    destination.getLongitude());

            destinationResponse.setImage(
                    destination.getImage());

            destinationResponse.setDayNumber(
                    packageDestination.getDayNumber());

            destinationResponse.setVisitOrder(
                    packageDestination.getVisitOrder());

            destinationResponse.setVisitDate(
                    packageDestination.getVisitDate());

            destinationResponses.add(destinationResponse);
        }

        response.setDestinations(destinationResponses);

        return response;
    }

    public List<TourPackageResponse> getAllPackages() {

        List<TourPackage> packages =
                tourPackageRepository.findAll();

        List<TourPackageResponse> responses =
                new ArrayList<>();

        for (TourPackage tourPackage : packages) {

            TourPackageResponse response =
                    getPackageById(tourPackage.getPackageId());

            responses.add(response);
        }

        return responses;
    }



    public List<TourPackageResponse> searchPackagesByKeyword(
            String keyword) {

        List<TourPackage> packages =
                tourPackageRepository.searchPackages(keyword);

        List<TourPackageResponse> responses =
                new ArrayList<>();

        for (TourPackage tourPackage : packages) {

            TourPackageResponse response =
                    getPackageById(tourPackage.getPackageId());

            responses.add(response);
        }

        return responses;
    }
}
