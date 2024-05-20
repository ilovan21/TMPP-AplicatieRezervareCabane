package org.example.response;

import java.math.BigDecimal;
import java.util.List;

public class VipCabinResponse extends CabinResponse{
    private boolean hasPrivatePool;
    private boolean hasJacuzzi;
    private boolean hasSauna;
    public VipCabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                            byte[] photoBytes , List<BookingResponse> bookings, boolean hasPrivatePool, boolean hasJacuzzi, boolean hasSauna) {
        super(id, location, price, isBooked, photoBytes, bookings);
        // caracteristici specifice
        this.hasPrivatePool = this.hasPrivatePool;
        this.hasJacuzzi = this.hasJacuzzi;
        this.hasSauna = this.hasSauna;
    }

    public VipCabinResponse(Long id, String location, double price, boolean booked, byte[] bytes, List<BookingResponse> collect, boolean hasPrivatePool, boolean hasJacuzzi, boolean hasSauna) {
    }
}
