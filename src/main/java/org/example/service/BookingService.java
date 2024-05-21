package org.example.service;

import org.example.exception.InvalidBookingRequestException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.BookedCabin;
import org.example.model.Cabin;
import org.example.repository.BookingRepository;
import org.example.response.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CabinService cabinService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CabinService cabinService) {
        this.bookingRepository = bookingRepository;
        this.cabinService = cabinService;
    }
    public List<BookedCabin> getAllBookings() {
        return bookingRepository.findAll();
    }

    public String saveBooking(Long cabinId, BookedCabin bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Cabin cabin = cabinService.getCabinById(cabinId).get();
        List<BookedCabin> existingBookings = cabin.getBookings();
        boolean cabinIsAvailable = cabinIsAvailable(bookingRequest,existingBookings);
        if (cabinIsAvailable){
            bookingRequest.setBookingConfirmationCode(UUID.randomUUID().toString());
            cabin.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        }else{
            throw  new InvalidBookingRequestException("Sorry, This cabin is not available for the selected dates;");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    private boolean cabinIsAvailable(BookedCabin bookingRequest, List<BookedCabin> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
    public BookedCabin findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+confirmationCode));

    }
}