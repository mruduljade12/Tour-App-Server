package com.mrudul.tourandtravel.service;


import com.mrudul.tourandtravel.dto.request.LeaveRequest;
import com.mrudul.tourandtravel.entity.LeaveApplication;
import com.mrudul.tourandtravel.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public LeaveApplication applyLeave(LeaveRequest request) {

        if (request.getDriverId() == null && request.getGuideId() == null) {
            throw new RuntimeException("Driver ID or Guide ID is required");
        }

        if (request.getDriverId() != null && request.getGuideId() != null) {
            throw new RuntimeException("Only Driver ID or Guide ID is allowed");
        }

        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Start date cannot be after end date");
        }

        LeaveApplication leave = new LeaveApplication();

        leave.setDriverId(request.getDriverId());
        leave.setGuideId(request.getGuideId());

        leave.setStartDate(startDate);
        leave.setEndDate(endDate);

        leave.setSubject(request.getSubject());
        leave.setDescription(request.getDescription());

        leave.setStatus("PENDING");
        leave.setAppliedDate(LocalDateTime.now());

        return leaveRepository.save(leave);
    }

    public boolean isDriverAvailable(
            Integer driverId,
            LocalDate date) {

        return !leaveRepository.isDriverOnLeave(
                driverId,
                date
        );
    }

    public boolean isGuideAvailable(
            Integer guideId,
            LocalDate date) {

        return !leaveRepository.isGuideOnLeave(
                guideId,
                date
        );
    }
}
