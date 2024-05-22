package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class StandardCabinResponse extends CabinResponse{
    private boolean hasFireplace;
    private boolean hasKitchen;
    private boolean hasBathroom;

    public StandardCabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                                 byte[] photoBytes , List<BookingResponse> bookings, boolean hasFireplace, boolean hasKitchen, boolean hasBathroom) {
        super(id, location, price, isBooked, photoBytes, bookings);
        // caracteristici specifice
        this.hasFireplace = hasFireplace;
        this.hasKitchen = hasKitchen;
        this.hasBathroom = hasBathroom;
    }
    public StandardCabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                                 byte[] photoBytes, boolean hasFireplace, boolean hasKitchen, boolean hasBathroom) {
        super(id, location, price, isBooked, photoBytes);
        this.hasFireplace = hasFireplace;
        this.hasKitchen = hasKitchen;
        this.hasBathroom = hasBathroom;
    }

}

