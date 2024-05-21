package org.example.responseStrategy;
import org.example.model.Cabin;
import org.example.model.VipCabin;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;
import org.example.response.VipCabinResponse;

import java.math.BigDecimal;
import java.util.List;

public class VipCabinResponseStrategy implements CabinResponseStrategy {

    @Override
    public CabinResponse createCabinResponse(Cabin cabin, byte[] photoBytes, List<BookingResponse> bookingInfo) {
        VipCabin vipCabin = (VipCabin) cabin;
        return new VipCabinResponse(
                vipCabin.getId(),
                vipCabin.getLocation(),
                BigDecimal.valueOf(vipCabin.getPrice()),
                vipCabin.isBooked(),
                photoBytes,
                bookingInfo,
                vipCabin.isHasPrivatePool(),
                vipCabin.isHasJacuzzi(),
                vipCabin.isHasSauna()
        );
    }
}