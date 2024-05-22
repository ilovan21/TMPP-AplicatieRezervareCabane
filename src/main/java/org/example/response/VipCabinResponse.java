package org.example.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class VipCabinResponse extends CabinResponse{
    private boolean hasPrivatePool;
    private boolean hasJacuzzi;
    private boolean hasSauna;
    public VipCabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                            byte[] photoBytes , List<BookingResponse> bookings, boolean hasPrivatePool, boolean hasJacuzzi, boolean hasSauna) {
        super(id, location, price, isBooked, photoBytes, bookings);
        // caracteristici specifice
        this.hasPrivatePool = hasPrivatePool;
        this.hasJacuzzi = hasJacuzzi;
        this.hasSauna = hasSauna;
    }
    //pentru rezervare raspuns
    public VipCabinResponse(Long id, String location, BigDecimal price, boolean isBooked,
                            byte[] photoBytes , boolean hasPrivatePool, boolean hasJacuzzi, boolean hasSauna) {
        super(id, location, price, isBooked, photoBytes);
        // caracteristici specifice
        this.hasPrivatePool = hasPrivatePool;
        this.hasJacuzzi = hasJacuzzi;
        this.hasSauna = hasSauna;
    }

    public boolean isHasPrivatePool() {
        return hasPrivatePool;
    }

    public void setHasPrivatePool(boolean hasPrivatePool) {
        this.hasPrivatePool = hasPrivatePool;
    }

    public boolean isHasJacuzzi() {
        return hasJacuzzi;
    }

    public void setHasJacuzzi(boolean hasJacuzzi) {
        this.hasJacuzzi = hasJacuzzi;
    }

    public boolean isHasSauna() {
        return hasSauna;
    }

    public void setHasSauna(boolean hasSauna) {
        this.hasSauna = hasSauna;
    }
}
