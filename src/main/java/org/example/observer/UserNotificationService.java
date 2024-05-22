package org.example.observer;

public class UserNotificationService implements Observer {
    @Override
    public void update(String message) {
        // Logica pentru trimiterea notificării utilizatorilor
        System.out.println("Notification: " + message);
    }
}
