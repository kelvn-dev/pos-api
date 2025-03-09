package net.vinpos.api.service.rest;

import java.io.*;
import java.util.*;
import net.vinpos.api.dto.rest.request.DishReqDto;
import net.vinpos.api.enums.*;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.mapping.rest.DishMapper;
import net.vinpos.api.model.Dish;
import net.vinpos.api.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DishService extends BaseService<Dish, DishRepository> {

  private final DishMapper dishMapper;

  public DishService(DishRepository repository, DishMapper dishMapper) {
    super(repository);
    this.dishMapper = dishMapper;
  }

  public Dish create(DishReqDto dto) {
    if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
      throw new ConflictException(modelClass, "name", dto.getName());
    }
    Dish dish = dishMapper.dto2Model(dto);
    return repository.save(dish);
  }

  public Dish updateById(UUID id, DishReqDto dto) {
    Dish dish = this.getById(id, false);
    if (!dish.getName().equalsIgnoreCase(dto.getName())) {
      if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
        throw new ConflictException(modelClass, "name", dto.getName());
      }
    }
    dishMapper.updateModelFromDto(dto, dish);
    return repository.save(dish);
  }
}
