package org.example.service;

import org.example.factory.CabinFactory;
import org.example.model.Cabin;
import org.example.model.StandardCabin;
import org.example.model.VipCabin;
import org.example.repository.CabinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CabinService implements ICabinService {
    private final CabinRepository cabinRepository;

    @Autowired
    public CabinService(CabinRepository cabinRepository) {
        this.cabinRepository = cabinRepository;
    }

    public Cabin createCabin(String type, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        Cabin cabin = CabinFactory.createCabin(type, location, price, isBooked, photo, numberOfRooms, specificProperties);
        return cabinRepository.save(cabin);
    }

    public Optional<Cabin> getCabinById(Long cabinId) {
        return cabinRepository.findById(cabinId);
    }

    @Override
    public List<Cabin> getAvailableCabins(LocalDate checkInDate, LocalDate checkOutDate, String cabinType) {
        // Implementare pentru returnarea cabinelor disponibile
        return null;
    }

    @Override
    public List<Cabin> getAllCabins() {
        return cabinRepository.findAll();
    }

    @Override
    public byte[] getCabinPhotoByCabinId(Long cabinId) throws SQLException {
        Optional<Cabin> cabinOptional = cabinRepository.findById(cabinId);
        if (cabinOptional.isPresent()) {
            Blob photoBlob = cabinOptional.get().getPhoto();
            if (photoBlob != null) {
                int blobLength = (int) photoBlob.length();
                return photoBlob.getBytes(1, blobLength);
            }
        }
        return new byte[0];
    }

    @Override
    public void deleteCabin(Long cabinId) {
        cabinRepository.deleteById(cabinId);
    }

    @Override
    public Cabin updateCabin(Long cabinId,String location, double price, boolean isBooked, byte photo_bytes, int numberOfRooms) {
        return null;
//    }
//    @Override
//    public Cabin updateCabin(Long cabinId, String location, double price, boolean isBooked, MultipartFile photo, int numberOfRooms, String type) {
//        Optional<Cabin> optionalCabin = cabinRepository.findById(cabinId);
//        if (optionalCabin.isPresent()) {
//            Cabin cabin = optionalCabin.get();
//            // Verificăm tipul cabinei și actualizăm în funcție de acesta
//            if (cabin instanceof StandardCabin) {
//                ((StandardCabin) cabin).update(location, price, isBooked, photo, numberOfRooms);
//            } else if (cabin instanceof VipCabin) {
//                ((VipCabin) cabin).update(location, price, isBooked, photo, numberOfRooms);
//            } else {
//                throw new IllegalArgumentException("Invalid cabin type: " + cabin.getClass().getSimpleName());
//            }
//            return cabinRepository.save(cabin);
//        } else {
//            throw new RuntimeException("Cabin not found with ID: " + cabinId);
//        }
//    }

}
}


