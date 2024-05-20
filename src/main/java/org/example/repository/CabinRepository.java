package org.example.repository;

import org.example.model.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabinRepository extends JpaRepository<Cabin, Long> {
    List<Cabin> findAll();
}
