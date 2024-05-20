package org.example.updateStrategy;

import org.example.model.Cabin;

import java.sql.Blob;
import java.util.Map;

public interface CabinUpdateStrategy {
    void updateCabin(Cabin cabin, Map<String, Object> specificProperties);
}
