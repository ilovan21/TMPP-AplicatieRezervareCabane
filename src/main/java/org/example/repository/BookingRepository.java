package org.example.repository;

import org.example.model.BookedCabin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<BookedCabin, Long> {

    List<BookedCabin> findByCabinId(Long roomId);

    Optional<BookedCabin> findByBookingConfirmationCode(String confirmationCode);

    //List<BookedCabin> findByGuestEmail(String email);
}
