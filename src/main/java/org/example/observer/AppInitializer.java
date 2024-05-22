package org.example.observer;

import org.example.service.BookingService;
import org.example.service.CabinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppInitializer {
    private final CabinService cabinService;
    private final BookingService bookingService;

    @Autowired
    public AppInitializer(CabinService cabinService, BookingService bookingService) {
        this.cabinService = cabinService;
        this.bookingService = bookingService;
        UserNotificationService notificationService = new UserNotificationService();
        cabinService.addObserver(notificationService);
        bookingService.addObserver(notificationService);
    }
}
