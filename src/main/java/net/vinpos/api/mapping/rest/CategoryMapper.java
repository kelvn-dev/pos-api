package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.CategoryReqDto;
import net.vinpos.api.dto.rest.response.CategoryResDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category dto2Model(CategoryReqDto dto);

  CategoryResDto model2Dto(Category user);

  void updateModelFromDto(CategoryReqDto dto, @MappingTarget Category dish);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
      source = "content",
      target = "items",
      defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<CategoryResDto> model2Dto(Page<Category> page);
}
