package org.example.repository;

import org.example.model.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CabinRepository extends JpaRepository<Cabin, Long> {
    List<Cabin> findAll();

    @Query(" SELECT c FROM Cabin c " +
            "WHERE c.id NOT IN (" +
            "  SELECT br.cabin.id FROM BookedCabin br " +
            "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
            ")")
    List<Cabin> findAvailableCabinsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate);
}
