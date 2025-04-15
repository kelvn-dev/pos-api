package net.vinpos.api.repository;


import net.vinpos.api.model.Order;
import net.vinpos.api.model.Shift;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShiftRepository extends BaseRepository<Shift, UUID> {

    @Query("SELECT s FROM Shift s WHERE s.isOpen = true AND s.startTime <= :currentTime")
    Optional<Shift> findOpenShift(Instant currentTime);
}
