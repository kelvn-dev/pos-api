package net.vinpos.api.repository;

import net.vinpos.api.model.Category;
import net.vinpos.api.model.Dish;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends BaseRepository<Category, UUID> {
    Optional<Category> findByNameIgnoreCase(String name);
}
