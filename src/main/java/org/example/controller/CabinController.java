package org.example.controller;

import org.example.updateStrategy.CabinUpdateStrategy;
import org.example.updateStrategy.StandardCabinUpdateStrategy;
import org.example.updateStrategy.VipCabinUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.example.exception.PhotoRetrievalException;
import org.example.model.Cabin;
import org.example.response.CabinResponse;
import org.example.service.CabinService;
import org.example.visitor.CabinResponseVisitor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cabins")
public class CabinController {

    private static final Logger logger = Logger.getLogger(CabinController.class.getName());
    private final CabinService cabinService;
    private final Map<String, CabinUpdateStrategy> updateStrategies;

    @Autowired
    public CabinController(CabinService cabinService) {
        this.cabinService = cabinService;

        // Inițializează strategiile de actualizare pentru fiecare tip de cabină
        updateStrategies = new HashMap<>();
        updateStrategies.put("standard", new StandardCabinUpdateStrategy());
        updateStrategies.put("vip", new VipCabinUpdateStrategy());
    }

    @GetMapping("/all")
    public ResponseEntity<List<CabinResponse>> getAllCabins() {
        List<Cabin> cabins = cabinService.getAllCabins();
        List<CabinResponse> cabinResponses = new ArrayList<>();
        for (Cabin cabin : cabins) {
            cabinResponses.add(getCabinResponse(cabin));
        }
        return ResponseEntity.ok(cabinResponses);
    }
    @Transactional(readOnly = true)
    public CabinResponse getCabinResponse(Cabin cabin) {
        byte[] photoBytes = null;
        Blob photoBlob = cabin.getPhoto();

        if (photoBlob != null) {
            try {
                logger.info("Attempting to retrieve photo bytes from Blob.");
                photoBlob.setBinaryStream(1);
                int blobLength = (int) photoBlob.length();
                logger.info("Blob length: " + blobLength);
                photoBytes = photoBlob.getBytes(1, blobLength);
            } catch (SQLException e) {
                logger.severe("Error retrieving photo: " + e.getMessage());
                e.printStackTrace();  // Logare stack trace complet pentru detalii suplimentare
                throw new PhotoRetrievalException("Error retrieving photo", e);
            }
        } else {
            logger.warning("Photo Blob is null for cabin ID: " + cabin.getId());
        }

        CabinResponseVisitor visitor = new CabinResponseVisitor();
        try {
            return cabin.accept(visitor, photoBytes, new ArrayList<>());
        } catch (SQLException e) {
            logger.severe("Error creating cabin response: " + e.getMessage());
            e.printStackTrace();  // Logare stack trace complet pentru detalii suplimentare
            throw new RuntimeException("Error creating cabin response", e);
        }
    }

    @PostMapping("/add-standard")

    public Cabin createStandardCabin(@RequestParam String location,
                                     @RequestParam double price,
                                     @RequestParam boolean isBooked,
                                     @RequestParam MultipartFile photo,
                                     @RequestParam int numberOfRooms,
                                     @RequestParam boolean hasFireplace,
                                     @RequestParam boolean hasKitchen,
                                     @RequestParam boolean hasBathroom) {
        Map<String, Object> specificProperties = new HashMap<>();
        specificProperties.put("hasFireplace", hasFireplace);
        specificProperties.put("hasKitchen", hasKitchen);
        specificProperties.put("hasBathroom", hasBathroom);
        Blob photoBlob = convertMultipartFileToBlob(photo);
        return cabinService.createCabin("STANDARD", location, price, isBooked, photoBlob, numberOfRooms, specificProperties);
    }

    @PostMapping("/add-vip")
    public Cabin createVipCabin(@RequestParam String location,
                                @RequestParam double price,
                                @RequestParam boolean isBooked,
                                @RequestParam MultipartFile photo,
                                @RequestParam int numberOfRooms,
                                @RequestParam boolean hasPrivatePool,
                                @RequestParam boolean hasJacuzzi,
                                @RequestParam boolean hasSauna) {
        Map<String, Object> specificProperties = new HashMap<>();
        specificProperties.put("hasPrivatePool", hasPrivatePool);
        specificProperties.put("hasJacuzzi", hasJacuzzi);
        specificProperties.put("hasSauna", hasSauna);
        Blob photoBlob = convertMultipartFileToBlob(photo);
        return cabinService.createCabin("VIP", location, price, isBooked, photoBlob, numberOfRooms, specificProperties);
    }

    private Blob convertMultipartFileToBlob(MultipartFile file) {
        try {
            return new javax.sql.rowset.serial.SerialBlob(file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert file to blob", e);
        }
    }
@PutMapping("/update/{cabinId}")
public ResponseEntity<CabinResponse> updateCabin(@RequestParam String type,
                                                 @PathVariable Long cabinId,
                                                 @RequestParam String location,
                                                 @RequestParam double price,
                                                 @RequestParam boolean isBooked,
                                                 @RequestParam MultipartFile photo,
                                                 @RequestParam int numberOfRooms,
                                                 @RequestParam Map<String, Object> specificProperties) {
    Cabin updatedCabin = cabinService.updateCabin(cabinId, location, price, isBooked, photo, numberOfRooms, type, specificProperties);
    CabinResponse cabinResponse = getCabinResponse(updatedCabin);
    return ResponseEntity.ok(cabinResponse);
}
    @DeleteMapping("/{cabinId}")
    public ResponseEntity<?> deleteCabin(@PathVariable Long cabinId) {
        cabinService.deleteCabin(cabinId);
        return ResponseEntity.ok("Cabin deleted successfully.");
    }
}
