package org.example.factory;

import org.example.model.Cabin;
import org.example.model.StandardCabin;
import org.example.model.VipCabin;

import java.sql.Blob;
import java.util.Map;

public class CabinFactory {
    public static Cabin createCabin(String type, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        if ("standard".equalsIgnoreCase(type)) {
            return createStandardCabin(location, price, isBooked, photo, numberOfRooms, specificProperties);
        } else if ("vip".equalsIgnoreCase(type)) {
            return createVipCabin(location, price, isBooked, photo, numberOfRooms, specificProperties);
        } else {
            throw new IllegalArgumentException("Unknown cabin type: " + type);
        }
    }

    private static StandardCabin createStandardCabin(String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        boolean hasFireplace = (Boolean) specificProperties.get("hasFireplace");
        boolean hasKitchen = (Boolean) specificProperties.get("hasKitchen");
        boolean hasBathroom = (Boolean) specificProperties.get("hasBathroom");

        return new StandardCabin(location, price, isBooked, photo, numberOfRooms, hasFireplace, hasKitchen, hasBathroom);
    }

    private static VipCabin createVipCabin(String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        boolean hasPrivatePool = (Boolean) specificProperties.get("hasPrivatePool");
        boolean hasJacuzzi = (Boolean) specificProperties.get("hasJacuzzi");
        boolean hasSauna = (Boolean) specificProperties.get("hasSauna");

        return new VipCabin(location, price, isBooked, photo, numberOfRooms, hasPrivatePool, hasJacuzzi, hasSauna);
    }
}




