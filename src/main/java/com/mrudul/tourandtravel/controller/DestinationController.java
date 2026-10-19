package com.mrudul.tourandtravel.controller;

import com.mrudul.tourandtravel.dto.request.DestinationRequest;
import com.mrudul.tourandtravel.entity.Destination;
import com.mrudul.tourandtravel.repository.DestinationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }


    @PostMapping
    public ResponseEntity<?> addDestination(
            @RequestBody DestinationRequest request) {

        Destination destination = new Destination();

        destination.setName(request.getName());
        destination.setCity(request.getCity());
        destination.setState(request.getState());
        destination.setCountry(request.getCountry());
        destination.setDescription(request.getDescription());
        destination.setLatitude(request.getLatitude());
        destination.setLongitude(request.getLongitude());
        destination.setImage(request.getImage());

        // Default status
        destination.setStatus("available");

        Destination savedDestination =
                destinationRepository.save(destination);

        return ResponseEntity.ok(savedDestination);
    }


    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

}