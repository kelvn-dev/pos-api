package net.vinpos.api.repository;

import java.util.Optional;
import java.util.UUID;
import net.vinpos.api.model.Dish;

public interface DishRepository extends BaseRepository<Dish, UUID> {
  Optional<Dish> findByNameIgnoreCase(String name);
}
