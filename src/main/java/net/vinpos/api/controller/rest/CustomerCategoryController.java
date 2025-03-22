package net.vinpos.api.controller.rest;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.mapping.rest.CategoryMapper;
import net.vinpos.api.model.Category;
import net.vinpos.api.service.rest.CategoryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer/categories")
@RequiredArgsConstructor
public class CustomerCategoryController {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable UUID id) {
    Category category = categoryService.getById(id, false);
    return ResponseEntity.ok(categoryMapper.model2Dto(category));
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
