package com.mrudul.tourandtravel.service;

import com.mrudul.tourandtravel.dto.request.BookingRequest;
import com.mrudul.tourandtravel.dto.response.BookingResponse;
import com.mrudul.tourandtravel.entity.Booking;
import com.mrudul.tourandtravel.entity.TourPackage;
import com.mrudul.tourandtravel.repository.BookingRepository;
import com.mrudul.tourandtravel.repository.TourPackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TourPackageRepository tourPackageRepository;

    public BookingService(
            BookingRepository bookingRepository,
            TourPackageRepository tourPackageRepository) {

        this.bookingRepository = bookingRepository;
        this.tourPackageRepository = tourPackageRepository;
    }


    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        // 1. Validate number of people
        if (request.getNumberOfPeople() == null ||
                request.getNumberOfPeople() <= 0) {

            throw new RuntimeException(
                    "Number of people must be greater than 0");
        }


        // 2. Find package
        TourPackage tourPackage = tourPackageRepository
                .findById(request.getPackageId())
                .orElseThrow(() ->
                        new RuntimeException("Package not found"));


        // 3. Get already booked people
        Long alreadyBooked =
                bookingRepository.getBookedPeople(
                        request.getPackageId());

        if (alreadyBooked == null) {
            alreadyBooked = 0L;
        }


        // 4. Check package capacity
        int totalPeople =
                alreadyBooked.intValue()
                        + request.getNumberOfPeople();

        if (totalPeople > tourPackage.getPackMaxCapacity()) {

            throw new RuntimeException(
                    "Booking failed. Package capacity exceeded. " +
                            "Available seats: "
                            + (tourPackage.getPackMaxCapacity()
                            - alreadyBooked));
        }


        // 5. Calculate total amount
        int totalAmount =
                tourPackage.getPackPrice()
                        * request.getNumberOfPeople();


        // 6. Create Booking
        Booking booking = new Booking();

        booking.setTourPackage(tourPackage);

        booking.setCustomerName(
                request.getCustomerName());

        booking.setCustomerEmail(
                request.getCustomerEmail());

        booking.setCustomerPhone(
                request.getCustomerPhone());

        booking.setNumberOfPeople(
                request.getNumberOfPeople());

        booking.setBookingDate(
                LocalDate.now());

        booking.setTotalAmount(totalAmount);

        booking.setStatus("pending");


        // 7. Save booking
        Booking savedBooking =
                bookingRepository.save(booking);


        // 8. Convert to response
        return convertToResponse(savedBooking);
    }


    // Convert Booking entity → BookingResponse
    private BookingResponse convertToResponse(
            Booking booking) {

        BookingResponse response =
                new BookingResponse();

        response.setBookingId(
                booking.getBookingId());

        response.setPackageId(
                booking.getTourPackage()
                        .getPackageId());

        response.setPackageName(
                booking.getTourPackage()
                        .getPackName());

        response.setCustomerName(
                booking.getCustomerName());

        response.setCustomerEmail(
                booking.getCustomerEmail());

        response.setCustomerPhone(
                booking.getCustomerPhone());

        response.setNumberOfPeople(
                booking.getNumberOfPeople());

        response.setBookingDate(
                booking.getBookingDate());

        response.setTotalAmount(
                booking.getTotalAmount());

        response.setStatus(
                booking.getStatus());

        return response;
    }


    public BookingResponse getBookingById(Integer bookingId) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        return convertToResponse(booking);
    }

    public List<BookingResponse> getAllBookings() {

        List<Booking> bookings =
                bookingRepository.findAll();

        List<BookingResponse> responses =
                new ArrayList<>();

        for (Booking booking : bookings) {

            responses.add(
                    convertToResponse(booking)
            );
        }

        return responses;
    }


    @Transactional
    public BookingResponse cancelBooking(Integer bookingId) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        // Check if already cancelled
        if ("cancelled".equalsIgnoreCase(booking.getStatus())) {

            throw new RuntimeException(
                    "Booking is already cancelled");
        }

        // Change booking status
        booking.setStatus("cancelled");

        // Save updated booking
        Booking updatedBooking =
                bookingRepository.save(booking);

        return convertToResponse(updatedBooking);
    }


    @Transactional
    public BookingResponse confirmBooking(Integer bookingId) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        // Check current status
        if ("confirmed".equalsIgnoreCase(booking.getStatus())) {

            throw new RuntimeException(
                    "Booking is already confirmed");
        }

        if ("cancelled".equalsIgnoreCase(booking.getStatus())) {

            throw new RuntimeException(
                    "Cancelled booking cannot be confirmed");
        }

        // Change status
        booking.setStatus("confirmed");

        // Save booking
        Booking updatedBooking =
                bookingRepository.save(booking);

        return convertToResponse(updatedBooking);
    }


    public List<BookingResponse> getCustomerBookings(
            String customerEmail) {

        List<Booking> bookings =
                bookingRepository
                        .findByCustomerEmailOrderByBookingIdDesc(
                                customerEmail);

        List<BookingResponse> responses =
                new ArrayList<>();

        for (Booking booking : bookings) {

            responses.add(
                    convertToResponse(booking)
            );
        }

        return responses;
    }


    public List<BookingResponse> getBookingsByStatus(
            String status) {

        if (!status.equalsIgnoreCase("pending")
                && !status.equalsIgnoreCase("confirmed")
                && !status.equalsIgnoreCase("cancelled")) {

            throw new RuntimeException(
                    "Invalid booking status. " +
                            "Use pending, confirmed or cancelled");
        }

        List<Booking> bookings =
                bookingRepository
                        .findByStatusOrderByBookingIdDesc(
                                status.toLowerCase());

        List<BookingResponse> responses =
                new ArrayList<>();

        for (Booking booking : bookings) {

            responses.add(
                    convertToResponse(booking)
            );
        }

        return responses;
    }
}
