package net.vinpos.api.controller.rest;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.controller.SecuredRestController;
import net.vinpos.api.dto.rest.request.CategoryReqDto;
import net.vinpos.api.mapping.rest.CategoryMapper;
import net.vinpos.api.model.Category;
import net.vinpos.api.service.rest.CategoryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
@Hidden
public class CategoryController implements SecuredRestController {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @PostMapping
  @PreAuthorize("hasAuthority('write:categories')")
  public ResponseEntity<?> create(@Valid @RequestBody CategoryReqDto dto) {
    Category category = categoryService.create(dto);
    return ResponseEntity.ok(categoryMapper.model2Dto(category));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Category category = categoryService.getById(id, false);
    return ResponseEntity.ok(categoryMapper.model2Dto(category));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('write:categories')")
  public ResponseEntity<?> updateById(
      @Valid @RequestBody CategoryReqDto dto, @PathVariable UUID id) {
    categoryService.updateById(id, dto);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('delete:categories')")
  public ResponseEntity<?> deleteById(@PathVariable UUID id) {
    categoryService.deleteById(id);
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
    return ResponseEntity.ok(categoryMapper.model2Dto(categoryService.getList(filter, pageable)));
  }
}
