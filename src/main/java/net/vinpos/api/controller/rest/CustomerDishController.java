package net.vinpos.api.controller.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.mapping.rest.DishMapper;
import net.vinpos.api.model.Dish;
import net.vinpos.api.service.rest.DishService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer/dishes")
@RequiredArgsConstructor
public class CustomerDishController {

  private final DishService dishService;
  private final DishMapper dishMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Dish dish = dishService.getById(id, false);
    return ResponseEntity.ok(dishMapper.model2Dto(dish));
  }

  @GetMapping
  public ResponseEntity<?> getList(
      @PageableDefault(
              sort = {"createdAt"},
              direction = Sort.Direction.DESC)
          @ParameterObject
          Pageable pageable,
      @RequestParam(required = false, defaultValue = "") List<String> filter) {
    return ResponseEntity.ok(dishMapper.model2Dto(dishService.getList(filter, pageable)));
  }
}
