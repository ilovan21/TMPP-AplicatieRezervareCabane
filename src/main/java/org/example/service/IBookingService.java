package org.example.service;

import org.example.model.BookedCabin;
import org.example.response.BookingResponse;

import java.util.List;


public interface IBookingService {
    void cancelBooking(Long bookingId);

    List<BookedCabin> getAllBookingsByCabinId(Long cabinId);

    String saveBooking(Long cabinId, BookedCabin bookingRequest);

    BookedCabin findByBookingConfirmationCode(String confirmationCode);

    List<BookedCabin> getAllBookings();

     BookingResponse findByConfirmationCode(String confirmationCode);
}
