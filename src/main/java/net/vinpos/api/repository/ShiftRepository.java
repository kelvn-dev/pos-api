package net.vinpos.api.repository;


import net.vinpos.api.model.Shift;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ShiftRepository extends BaseRepository<Shift, UUID> {

    @Query("SELECT s FROM Shift s WHERE s.manager.id = :id AND s.isOpen = true AND s.startTime <= :currentTime")
    Optional<Shift> findOpenShiftByManager(String id, Instant currentTime);
}
