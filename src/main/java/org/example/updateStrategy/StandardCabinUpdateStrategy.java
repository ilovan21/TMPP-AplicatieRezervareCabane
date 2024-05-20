package org.example.updateStrategy;

import org.example.model.Cabin;
import org.example.model.StandardCabin;

import java.util.Map;

public class StandardCabinUpdateStrategy implements CabinUpdateStrategy {
    @Override
    public void updateCabin(Cabin cabin, Map<String, Object> specificProperties) {
        if (cabin instanceof StandardCabin) {
            StandardCabin standardCabin = (StandardCabin) cabin;
            standardCabin.setHasFireplace((Boolean) specificProperties.get("hasFireplace"));
            standardCabin.setHasKitchen((Boolean) specificProperties.get("hasKitchen"));
            standardCabin.setHasBathroom((Boolean) specificProperties.get("hasBathroom"));
        } else {
            throw new IllegalArgumentException("Invalid cabin type");
        }
    }
}
