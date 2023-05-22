package com.sau.sbsecurity.Repositories;

import com.sau.sbsecurity.Models.Rental;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r WHERE r.isDeleted = false")
    Iterable<Rental> getAll();

    @Modifying
    @Transactional
    @Query("UPDATE Rental r SET r.isDeleted = true WHERE r.id = :id")
    void setDeleted(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE Rental r SET r.isReturned = true WHERE r.id = :id")
    void setReturned(@Param("id") int id);
}
