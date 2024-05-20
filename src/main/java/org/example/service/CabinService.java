package org.example.service;

import org.example.factory.CabinFactory;
import org.example.model.Cabin;
import org.example.repository.CabinRepository;
import org.example.updateStrategy.CabinUpdateStrategy;
import org.example.updateStrategy.StandardCabinUpdateStrategy;
import org.example.updateStrategy.VipCabinUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
// implements ICabinService
@Service
public class CabinService {
    private final CabinRepository cabinRepository;
    private final Map<String, CabinUpdateStrategy> updateStrategies;

    @Autowired
    public CabinService(CabinRepository cabinRepository, Map<String, CabinUpdateStrategy> updateStrategies) {
        this.cabinRepository = cabinRepository;
        this.updateStrategies = updateStrategies;

        // Inițializează strategiile de actualizare pentru fiecare tip de cabină
        updateStrategies.put("standard", new StandardCabinUpdateStrategy());
        updateStrategies.put("vip", new VipCabinUpdateStrategy());
        // Adăugați aici și alte tipuri de cabină și strategiile corespunzătoare, dacă există
    }

    public Cabin createCabin(String type, String location, double price, boolean isBooked, Blob photo, int numberOfRooms, Map<String, Object> specificProperties) {
        Cabin cabin = CabinFactory.createCabin(type, location, price, isBooked, photo, numberOfRooms, specificProperties);
        return cabinRepository.save(cabin);
    }


    //@Override
    public List<Cabin> getAllCabins() {
        return cabinRepository.findAll();
    }

    //@Override
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

    //@Override
    public void deleteCabin(Long cabinId) {
        cabinRepository.deleteById(cabinId);
    }

public Cabin updateCabin(Long cabinId, String location, double price, boolean isBooked, MultipartFile photo, int numberOfRooms, String type, Map<String, Object> specificProperties) {
    Optional<Cabin> optionalCabin = cabinRepository.findById(cabinId);
    if (optionalCabin.isPresent()) {
        Cabin cabin = optionalCabin.get();
        CabinUpdateStrategy strategy = updateStrategies.get(type.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid cabin type: " + type);
        }
        // Actualizează datele cabinei
        cabin.setLocation(location);
        cabin.setPrice(price);
        cabin.setBooked(isBooked);
        cabin.setNumberOfRooms(numberOfRooms);

        // Convertirea fișierului MultipartFile într-un obiect Blob
        Blob photoBlob = convertMultipartFileToBlob(photo);

        // Actualizează proprietățile specifice ale cabinei în funcție de tip
        strategy.updateCabin(cabin, specificProperties);

        // Salvare cabină actualizată în baza de date
        return cabinRepository.save(cabin);
    } else {
        throw new RuntimeException("Cabin not found with ID: " + cabinId);
    }
}
    private Blob convertMultipartFileToBlob(MultipartFile file) {
        try {
            return new javax.sql.rowset.serial.SerialBlob(file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert file to blob", e);
        }
    }
    public Optional<Cabin> getCabinById(Long cabinId) {
        return Optional.of(cabinRepository.findById(cabinId).get());
    }
    public List<Cabin> getAvailableCabins(LocalDate checkInDate, LocalDate checkOutDate) {
        return cabinRepository.findAvailableCabinsByDatesAndType(checkInDate, checkOutDate);
    }
}



