package org.example.responseStrategy;

import org.example.model.Cabin;
import org.example.model.StandardCabin;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;
import org.example.response.StandardCabinResponse;

import java.math.BigDecimal;
import java.util.List;

public class StandardCabinResponseStrategy implements CabinResponseStrategy {
    @Override
    public CabinResponse createCabinResponse(Cabin cabin, byte[] photoBytes, List<BookingResponse> bookingInfo) {
        StandardCabin standardCabin = (StandardCabin) cabin;
        return new StandardCabinResponse(
                standardCabin.getId(),
                standardCabin.getLocation(),
                BigDecimal.valueOf(standardCabin.getPrice()),
                standardCabin.isBooked(),
                photoBytes,
                bookingInfo,
                standardCabin.isHasFireplace(),
                standardCabin.isHasKitchen(),
                standardCabin.isHasBathroom()
        );
    }
}