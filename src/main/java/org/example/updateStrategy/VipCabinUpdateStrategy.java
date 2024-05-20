package org.example.updateStrategy;
import org.example.model.Cabin;
import org.example.model.VipCabin;

import java.sql.Blob;
import java.util.Map;

public class VipCabinUpdateStrategy implements CabinUpdateStrategy {

    @Override
    public Cabin updateCabin(Cabin cabin, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        if (cabin instanceof VipCabin) {
            VipCabin vipCabin = (VipCabin) cabin;
            vipCabin.setLocation(location);
            vipCabin.setPrice(price);
            vipCabin.setBooked(isBooked);
            vipCabin.setNumberOfRooms(numberOfRooms);

            if (specificProperties != null) {
                Object hasPrivatePool = specificProperties.get("hasPrivatePool");
                Object hasJacuzzi = specificProperties.get("hasJacuzzi");
                Object hasSauna = specificProperties.get("hasSauna");

                if (hasPrivatePool instanceof Boolean) {
                    vipCabin.setHasPrivatePool((Boolean) hasPrivatePool);
                }
                if (hasJacuzzi instanceof Boolean) {
                    vipCabin.setHasJacuzzi((Boolean) hasJacuzzi);
                }
                if (hasSauna instanceof Boolean) {
                    vipCabin.setHasSauna((Boolean) hasSauna);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid cabin type for VipCabinUpdateStrategy");
        }
        return cabin;
    }
}
