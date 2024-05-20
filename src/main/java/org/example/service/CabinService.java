package org.example.service;

import org.example.factory.CabinFactory;
import org.example.model.Cabin;
import org.example.repository.CabinRepository;
import org.example.updateStrategy.CabinUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CabinService implements ICabinService {
    private final CabinRepository cabinRepository;
    private final Map<String, CabinUpdateStrategy> updateStrategies;

    @Autowired
    public CabinService(CabinRepository cabinRepository, Map<String, CabinUpdateStrategy> updateStrategies) {
        this.cabinRepository = cabinRepository;
        this.updateStrategies = updateStrategies;
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

//    @Override
//    public Cabin updateCabin(Long cabinId,String location, double price, boolean isBooked, byte photo_bytes, int numberOfRooms) {
//        return null; }
    @Override
    public Cabin updateCabin(Long cabinId, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, String type, Map<String, Object> specificProperties) {
        Optional<Cabin> optionalCabin = cabinRepository.findById(cabinId);
        if (optionalCabin.isPresent()) {
            Cabin cabin = optionalCabin.get();
            CabinUpdateStrategy strategy = updateStrategies.get(type.toLowerCase());
            if (strategy == null) {
                throw new IllegalArgumentException("Invalid cabin type: " + type);
            }
            strategy.updateCabin(cabin, location, price, isBooked, photo, numberOfRooms, specificProperties);
            return cabinRepository.save(cabin);
        } else {
            throw new RuntimeException("Cabin not found with ID: " + cabinId);
        }
    }
}


