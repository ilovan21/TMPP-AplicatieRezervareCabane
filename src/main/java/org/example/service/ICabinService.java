package org.example.service;
import org.example.model.Cabin;
import org.example.response.BookingResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ICabinService {

    List<Cabin> getAllCabins();

    byte[] getCabinPhotoByCabinId(Long cabinId) throws SQLException;

    void deleteCabin(Long cabinId);

    Cabin updateCabin(Long cabinId,String location, double price, boolean isBooked, byte photo_bytes, int numberOfRooms);

    Optional<Cabin> getCabinById(Long cabinId);

    List<Cabin> getAvailableCabins(LocalDate checkInDate, LocalDate checkOutDate, String cabinType);

}