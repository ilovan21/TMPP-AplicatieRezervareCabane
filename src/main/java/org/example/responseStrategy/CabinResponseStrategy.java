package org.example.responseStrategy;

import org.example.model.Cabin;
import org.example.response.BookingResponse;
import org.example.response.CabinResponse;

import java.sql.Blob;
import java.util.List;

public interface CabinResponseStrategy {
    CabinResponse createCabinResponse(Cabin cabin, byte[] photoBytes);
}
