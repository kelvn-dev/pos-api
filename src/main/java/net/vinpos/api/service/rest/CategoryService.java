package net.vinpos.api.service.rest;

import net.vinpos.api.dto.rest.request.CategoryReqDto;
import net.vinpos.api.dto.rest.request.DishReqDto;
import net.vinpos.api.exception.ConflictException;
import net.vinpos.api.mapping.rest.CategoryMapper;
import net.vinpos.api.mapping.rest.DishMapper;
import net.vinpos.api.model.Category;
import net.vinpos.api.model.Dish;
import net.vinpos.api.repository.CategoryRepository;
import net.vinpos.api.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Executor;

@Service
public class CategoryService extends BaseService<Category, CategoryRepository> {

  private final CategoryMapper categoryMapper;

  public CategoryService(CategoryRepository repository, CategoryMapper categoryMapper) {
    super(repository);
      this.categoryMapper = categoryMapper;
  }

  public Category create(CategoryReqDto dto) {
    if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
      throw new ConflictException(modelClass, "name", dto.getName());
    }
    Category category = categoryMapper.dto2Model(dto);
    return repository.save(category);
  }

  public Category updateById(UUID id, CategoryReqDto dto) {
    Category category = this.getById(id, false);
    if (!category.getName().equalsIgnoreCase(dto.getName())) {
      if (repository.findByNameIgnoreCase(dto.getName()).isPresent()) {
        throw new ConflictException(modelClass, "name", dto.getName());
      }
    }
    categoryMapper.updateModelFromDto(dto, category);
    return repository.save(category);
  }
}
