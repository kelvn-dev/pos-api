package net.vinpos.api.repository;

import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.model.Category;

public interface CategoryRepository extends BaseRepository<Category, UUID> {
  Optional<Category> findByNameIgnoreCase(String name);
}
