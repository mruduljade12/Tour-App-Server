package com.mrudul.tourandtravel.controller;


import com.mrudul.tourandtravel.dto.request.BusRequest;
import com.mrudul.tourandtravel.entity.Bus;
import com.mrudul.tourandtravel.repository.BusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusRepository busRepository;

    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }


    @PostMapping
    public ResponseEntity<?> addBus(@RequestBody BusRequest request) {

        Bus bus = new Bus();

        bus.setBusNumber(request.getBusNumber());
        bus.setBusName(request.getBusName());
        bus.setBusType(request.getBusType());
        bus.setBusCapacity(request.getBusCapacity());

        // Default status
        bus.setStatus("active");

        Bus savedBus = busRepository.save(bus);

        return ResponseEntity.ok(savedBus);
    }

    @GetMapping
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }
}
