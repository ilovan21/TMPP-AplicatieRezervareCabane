package org.example.model;
import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.example.response.BookingResponse;
import org.example.visitor.CabinVisitor;
import org.example.response.CabinResponse;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cabin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private double price;
    private boolean isBooked;
    @Lob
    private Blob photo;
    private int numberOfRooms;
    @OneToMany(mappedBy="cabin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedCabin> bookings;

    public Cabin() {
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedCabin booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setCabin(this);
        isBooked = true;
    }

    public Cabin(String location, double price, boolean isBooked, Blob photo, int numberOfRooms) {
        this.location = location;
        this.price = price;
        this.isBooked = isBooked;
        this.photo = photo;
        this.numberOfRooms = numberOfRooms;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public Blob getPhoto() {
        return photo;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public List<BookedCabin> getBookings() {
        return bookings;
    }

    public abstract CabinResponse accept(CabinVisitor visitor, byte[] photoBytes, List<BookingResponse> bookingInfo) throws SQLException;
}


