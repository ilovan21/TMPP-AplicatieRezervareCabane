
package org.example.model;

import jakarta.persistence.Entity;
import org.example.response.BookingResponse;
import org.example.visitor.CabinVisitor;
import org.example.response.CabinResponse;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Entity
public class VipCabin extends Cabin {
    private boolean hasPrivatePool;
    private boolean hasJacuzzi;
    private boolean hasSauna;

    public VipCabin(String location, double price, boolean isBooked, Blob photo, int numberOfRooms, boolean hasPrivatePool, boolean hasJacuzzi, boolean hasSauna) {
        super(location, price, isBooked, photo, numberOfRooms);
        this.hasPrivatePool = hasPrivatePool;
        this.hasJacuzzi = hasJacuzzi;
        this.hasSauna = hasSauna;
    }

    public VipCabin() {}

    public boolean isHasPrivatePool() {
        return hasPrivatePool;
    }

    public boolean isHasJacuzzi() {
        return hasJacuzzi;
    }

    public boolean isHasSauna() {
        return hasSauna;
    }

    @Override
    public CabinResponse accept(CabinVisitor visitor, byte[] photoBytes, List<BookingResponse> bookingInfo) throws SQLException {
        return visitor.visit(this, photoBytes, bookingInfo);
    }
}

