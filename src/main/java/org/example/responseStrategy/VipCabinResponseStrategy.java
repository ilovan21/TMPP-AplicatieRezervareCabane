package org.example.responseStrategy;
import org.example.model.Cabin;
import org.example.model.VipCabin;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;
import org.example.response.VipCabinResponse;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public class VipCabinResponseStrategy implements CabinResponseStrategy {

    @Override
    public CabinResponse createCabinResponse(Cabin cabin, byte[] photoBytes) {
        VipCabin vipCabin = (VipCabin) cabin;
        return new VipCabinResponse(
                vipCabin.getId(),
                vipCabin.getLocation(),
                BigDecimal.valueOf(vipCabin.getPrice()),
                vipCabin.isBooked(),
                photoBytes,
                vipCabin.isHasPrivatePool(),
                vipCabin.isHasJacuzzi(),
                vipCabin.isHasSauna()
        );
    }
}