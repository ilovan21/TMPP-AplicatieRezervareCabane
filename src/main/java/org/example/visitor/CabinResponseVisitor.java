package org.example.visitor;

import org.example.model.StandardCabin;
import org.example.model.VipCabin;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;
import org.example.response.StandardCabinResponse;
import org.example.response.VipCabinResponse;

import java.math.BigDecimal;
import java.util.List;

public class CabinResponseVisitor implements CabinVisitor {

    @Override
    public CabinResponse visit(StandardCabin standardCabin, byte[] photoBytes, List<BookingResponse> bookingInfo) {
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

    @Override
    public CabinResponse visit(VipCabin vipCabin, byte[] photoBytes, List<BookingResponse> bookingInfo) {
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
