package net.vinpos.api.repository;

import net.vinpos.api.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishRepository extends BaseRepository<Dish, UUID> {
    Optional<Dish> findByNameIgnoreCase(String name);
}
