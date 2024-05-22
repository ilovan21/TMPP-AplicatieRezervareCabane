package org.example.controller;

import org.example.exception.InvalidBookingRequestException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.BookedCabin;
import org.example.model.Cabin;
import org.example.repository.BookingRepository;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;
import org.example.responseStrategy.CabinResponseStrategy;
import org.example.responseStrategy.CabinResponseStrategyFactory;
import org.example.service.BookingService;
import org.example.service.CabinService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CabinService cabinService;


    @Autowired
    public BookingController(BookingService bookingService, CabinService cabinService) {
        this.bookingService = bookingService;
        this.cabinService=cabinService;
    }

    @PostMapping("/cabin/{cabinId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long cabinId,
                                         @RequestBody BookedCabin bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(cabinId, bookingRequest);
            return ResponseEntity.ok(
                    "Cabin booked successfully, Your booking confirmation code is :"+confirmationCode);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
        try {
            BookedCabin booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    private BookingResponse getBookingResponse(BookedCabin booking) {
        Cabin theCabin = cabinService.getCabinById(booking.getCabin().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cabin not found"));
        Hibernate.initialize(theCabin);
        byte[] photoBytes = getPhotoBytes(theCabin.getPhoto());
        List<BookingResponse> bookingInfo = bookingService.getAllBookings().stream()
                .map(b -> new BookingResponse(
                        b.getBookingId(),
                        b.getCheckInDate(),
                        b.getCheckOutDate(),
                        b.getGuestFullName(),
                        b.getGuestEmail(),
                        b.getNumOfAdults(),
                        b.getNumOfChildren(),
                        b.getTotalNumOfGuest(),
                        b.getBookingConfirmationCode(),
                        null))
                .collect(Collectors.toList());

        CabinResponseStrategy strategy = CabinResponseStrategyFactory.getStrategy(theCabin);
        CabinResponse cabinResponse = strategy.createCabinResponse(theCabin, photoBytes);

        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(),//);
                cabinResponse);
    }

    private byte[] getPhotoBytes(Blob photoBlob) {
        if (photoBlob == null) {
            return null;
        }
        try (InputStream inputStream = photoBlob.getBinaryStream()) {
            return inputStream.readAllBytes();
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to retrieve photo bytes", e);
        }
    }
    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedCabin> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedCabin booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Booking deleted successfully.");
    }
}