package org.example.responseStrategy;

import org.example.model.Cabin;
import org.example.model.StandardCabin;
import org.example.model.VipCabin;

public class CabinResponseStrategyFactory {

    public static CabinResponseStrategy getStrategy(Cabin cabin) {
        if (cabin instanceof Cabin) {
            return new StandardCabinResponseStrategy();
//        } else if (cabin instanceof VipCabin) {
//            return new VipCabinResponseStrategy();
//        } else {
//            throw new IllegalArgumentException("Unknown cabin type");
//        }
        }
        return null;
    }
}
