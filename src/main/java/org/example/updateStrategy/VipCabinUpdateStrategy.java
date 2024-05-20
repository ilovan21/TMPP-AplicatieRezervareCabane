package org.example.updateStrategy;
import org.example.model.Cabin;
import org.example.model.VipCabin;

import java.sql.Blob;
import java.util.Map;

public class VipCabinUpdateStrategy implements CabinUpdateStrategy {
    public void updateCabin(Cabin cabin, Map<String, Object> specificProperties) {
        if (cabin instanceof VipCabin) {
            VipCabin vipCabin = (VipCabin) cabin;

            // Verifică dacă cheile sunt prezente în map și dacă valorile sunt de tipul așteptat
            if (specificProperties.containsKey("hasPrivatePool") && specificProperties.get("hasPrivatePool") instanceof String) {
                String hasPrivatePoolString = (String) specificProperties.get("hasPrivatePool");
                boolean hasPrivatePool = hasPrivatePoolString.equalsIgnoreCase("yes");
                vipCabin.setHasPrivatePool(hasPrivatePool);
            } else {
                throw new IllegalArgumentException("Invalid value for hasPrivatePool");
            }

            if (specificProperties.containsKey("hasJacuzzi") && specificProperties.get("hasJacuzzi") instanceof String) {
                String hasJacuzziString = (String) specificProperties.get("hasJacuzzi");
                boolean hasJacuzzi = hasJacuzziString.equalsIgnoreCase("yes");
                vipCabin.setHasJacuzzi(hasJacuzzi);
            } else {
                throw new IllegalArgumentException("Invalid value for hasJacuzzi");
            }

            if (specificProperties.containsKey("hasSauna") && specificProperties.get("hasSauna") instanceof String) {
                String hasSaunaString = (String) specificProperties.get("hasSauna");
                boolean hasSauna = hasSaunaString.equalsIgnoreCase("yes");
                vipCabin.setHasSauna(hasSauna);
            } else {
                throw new IllegalArgumentException("Invalid value for hasSauna");
            }
        } else {
            throw new IllegalArgumentException("Invalid cabin type");
        }
    }


}
