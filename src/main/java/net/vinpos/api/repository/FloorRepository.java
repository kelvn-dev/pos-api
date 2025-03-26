package net.vinpos.api.repository;

import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.model.Floor;

public interface FloorRepository extends BaseRepository<Floor, UUID> {
  Optional<Floor> findByNameIgnoreCase(String name);
}
