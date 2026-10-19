package com.mrudul.tourandtravel.controller;

import com.mrudul.tourandtravel.dto.request.LeaveRequest;
import com.mrudul.tourandtravel.dto.request.LeaveStatusRequest;
import com.mrudul.tourandtravel.entity.LeaveApplication;
import com.mrudul.tourandtravel.repository.LeaveRepository;
import com.mrudul.tourandtravel.service.LeaveService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;
    private final LeaveRepository leaveRepository;

    public LeaveController(
            LeaveService leaveService,
            LeaveRepository leaveRepository) {

        this.leaveService = leaveService;
        this.leaveRepository = leaveRepository;
    }

    @PostMapping
    public ResponseEntity<?> applyLeave(
            @RequestBody LeaveRequest request) {

        try {

            LeaveApplication leave =
                    leaveService.applyLeave(request);

            return ResponseEntity.ok(leave);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/driver/{driverId}")
    public List<LeaveApplication> getDriverLeaves(
            @PathVariable Integer driverId) {

        return leaveRepository.findByDriverId(driverId);
    }

    @GetMapping("/guide/{guideId}")
    public List<LeaveApplication> getGuideLeaves(
            @PathVariable Integer guideId) {

        return leaveRepository.findByGuideId(guideId);
    }

    @GetMapping("/pending")
    public List<LeaveApplication> getPendingLeaves() {

        return leaveRepository.findByStatus("PENDING");
    }

    @PutMapping("/{leaveId}/status")
    public ResponseEntity<?> updateLeaveStatus(
            @PathVariable Integer leaveId,
            @RequestBody LeaveStatusRequest request) {

        LeaveApplication leave =
                leaveRepository.findById(leaveId).orElse(null);

        if (leave == null) {
            return ResponseEntity.notFound().build();
        }

        String status = request.getStatus();

        if (!status.equals("APPROVED")
                && !status.equals("REJECTED")) {

            return ResponseEntity.badRequest()
                    .body("Invalid status");
        }

        leave.setStatus(status);

        if (status.equals("APPROVED")) {
            leave.setApprovedDate(LocalDateTime.now());
        }

        leaveRepository.save(leave);

        return ResponseEntity.ok(leave);
    }


    @GetMapping("/driver/{driverId}/availability")
    public ResponseEntity<?> checkDriverAvailability(
            @PathVariable Integer driverId,
            @RequestParam String date) {

        LocalDate localDate =
                LocalDate.parse(date);

        boolean available =
                leaveService.isDriverAvailable(
                        driverId,
                        localDate
                );

        return ResponseEntity.ok(available);
    }


    @GetMapping("/guide/{guideId}/availability")
    public ResponseEntity<?> checkGuideAvailability(
            @PathVariable Integer guideId,
            @RequestParam String date) {

        LocalDate localDate =
                LocalDate.parse(date);

        boolean available =
                leaveService.isGuideAvailable(
                        guideId,
                        localDate
                );

        return ResponseEntity.ok(available);
    }
}