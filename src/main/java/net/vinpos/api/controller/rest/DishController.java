package net.vinpos.api.controller.rest;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.DishReqDto;
import net.vinpos.api.mapping.rest.DishMapper;
import net.vinpos.api.model.Dish;
import net.vinpos.api.service.rest.DishService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/dishes")
@RequiredArgsConstructor
@Hidden
public class DishController implements SecuredRestController {

  private final DishService dishService;
  private final DishMapper dishMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('write:dishes')")
  public ResponseEntity<?> create(@Valid @RequestBody DishReqDto dto) {
    dishService.create(dto);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Dish dish = dishService.getById(id, false);
    return ResponseEntity.ok(dishMapper.model2Dto(dish));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('write:dishes')")
  public ResponseEntity<?> updateById(@Valid @RequestBody DishReqDto dto, @PathVariable UUID id) {
    dishService.updateById(id, dto);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('delete:dishes')")
  public ResponseEntity<?> deleteById(@PathVariable UUID id) {
    dishService.deleteById(id);
    return ResponseEntity.ok(null);
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
