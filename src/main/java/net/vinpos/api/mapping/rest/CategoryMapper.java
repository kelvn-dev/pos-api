package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.CategoryReqDto;
import net.vinpos.api.dto.rest.response.CategoryResDto;
import net.vinpos.api.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

  Category dto2Model(CategoryReqDto dto);

  CategoryResDto model2Dto(Category user);

  void updateModelFromDto(CategoryReqDto dto, @MappingTarget Category dish);
}
