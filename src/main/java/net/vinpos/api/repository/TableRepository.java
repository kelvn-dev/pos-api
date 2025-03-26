package net.vinpos.api.repository;

import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.model.TableEntity;

public interface TableRepository extends BaseRepository<TableEntity, UUID> {
  Optional<TableEntity> findByNumber(Integer number);
}
