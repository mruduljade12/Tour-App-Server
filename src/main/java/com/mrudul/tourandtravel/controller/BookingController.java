package com.mrudul.tourandtravel.controller;


import com.mrudul.tourandtravel.dto.request.BookingRequest;
import com.mrudul.tourandtravel.dto.response.BookingResponse;
import com.mrudul.tourandtravel.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {

        this.bookingService = bookingService;
    }


    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody BookingRequest request) {

        BookingResponse response = bookingService.createBooking(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {

        List<BookingResponse> responses = bookingService.getAllBookings();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Integer bookingId) {

        BookingResponse response =
                bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Integer bookingId) {

        BookingResponse response =
                bookingService.cancelBooking(bookingId);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(
            @PathVariable Integer bookingId) {

        BookingResponse response =
                bookingService.confirmBooking(bookingId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<BookingResponse>> getCustomerBookings(
            @RequestParam String email) {

        List<BookingResponse> responses =
                bookingService.getCustomerBookings(email);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingResponse>> getBookingsByStatus(
            @PathVariable String status) {

        List<BookingResponse> responses =
                bookingService.getBookingsByStatus(status);

        return ResponseEntity.ok(responses);
    }
}
