package org.example.updateStrategy;

import org.example.model.Cabin;

import java.sql.Blob;
import java.util.Map;

public interface CabinUpdateStrategy {
    Cabin updateCabin(Cabin cabin, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties);
}
