package org.example.model;

import jakarta.persistence.Entity;
import org.example.response.BookingResponse;
import org.example.visitor.CabinVisitor;
import org.example.response.CabinResponse;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Entity
public class StandardCabin extends Cabin {
    private boolean hasFireplace;
    private boolean hasKitchen;
    private boolean hasBathroom;

    public StandardCabin(String location, double price, boolean isBooked, Blob photo, int numberOfRooms,
                         boolean hasFireplace, boolean hasKitchen, boolean hasBathroom) {
        super(location, price, isBooked, photo, numberOfRooms);
        this.hasFireplace = hasFireplace;
        this.hasKitchen = hasKitchen;
        this.hasBathroom = hasBathroom;
    }

    public void setHasFireplace(boolean hasFireplace) {
        this.hasFireplace = hasFireplace;
    }

    public void setHasBathroom(boolean hasBathroom) {
        this.hasBathroom = hasBathroom;
    }

    public void setHasKitchen(boolean hasKitchen) {
        this.hasKitchen = hasKitchen;
    }

    public StandardCabin() {}

    public boolean isHasFireplace() {
        return hasFireplace;
    }

    public boolean isHasKitchen() {
        return hasKitchen;
    }

    public boolean isHasBathroom() {
        return hasBathroom;
    }

    @Override
    public CabinResponse accept(CabinVisitor visitor, byte[] photoBytes, List<BookingResponse> bookingInfo) throws SQLException {
        return visitor.visit(this, photoBytes, bookingInfo);
    }
}
