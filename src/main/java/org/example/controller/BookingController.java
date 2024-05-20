package org.example.controller;

import org.example.exception.InvalidBookingRequestException;
import org.example.model.BookedCabin;
import org.example.repository.BookingRepository;
import org.example.response.BookingResponse;
import org.example.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
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
}

