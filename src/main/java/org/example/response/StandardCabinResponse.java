package org.example.response;

import java.math.BigDecimal;
import java.util.List;

public class StandardCabinResponse extends CabinResponse{
    private boolean hasFireplace;
    private boolean hasKitchen;
    private boolean hasBathroom;

    public StandardCabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                                 byte[] photoBytes , List<BookingResponse> bookings, boolean hasFireplace, boolean hasKitchen, boolean hasBathroom) {
        super(id, location, price, isBooked, photoBytes, bookings);
        // caracteristici specifice
        this.hasFireplace = this.hasFireplace;
        this.hasKitchen = this.hasKitchen;
        this.hasBathroom = this.hasBathroom;
    }

    public StandardCabinResponse(Long id, String location, double price, boolean booked, byte[] bytes, List<BookingResponse> collect, boolean hasFireplace, boolean hasKitchen, boolean hasBathroom) {
    }
}

