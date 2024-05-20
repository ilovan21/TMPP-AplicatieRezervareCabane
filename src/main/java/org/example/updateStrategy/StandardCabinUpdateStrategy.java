package org.example.updateStrategy;

import org.example.model.Cabin;
import org.example.model.StandardCabin;

import java.sql.Blob;
import java.util.Map;

public class StandardCabinUpdateStrategy implements CabinUpdateStrategy {

    @Override
    public Cabin updateCabin(Cabin cabin, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        if (cabin instanceof StandardCabin) {
            StandardCabin standardCabin = (StandardCabin) cabin;
            standardCabin.setLocation(location);
            standardCabin.setPrice(price);
            standardCabin.setBooked(isBooked);
            standardCabin.setNumberOfRooms(numberOfRooms);

            if (specificProperties != null) {
                Object hasFireplace = specificProperties.get("hasFireplace");
                Object hasKitchen = specificProperties.get("hasKitchen");
                Object hasBathroom = specificProperties.get("hasBathroom");

                if (hasFireplace instanceof Boolean) {
                    standardCabin.setHasFireplace((Boolean) hasFireplace);
                }
                if (hasKitchen instanceof Boolean) {
                    standardCabin.setHasKitchen((Boolean) hasKitchen);
                }
                if (hasBathroom instanceof Boolean) {
                    standardCabin.setHasBathroom((Boolean) hasBathroom);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid cabin type for StandardCabinUpdateStrategy");
        }
        return cabin;
    }
}
