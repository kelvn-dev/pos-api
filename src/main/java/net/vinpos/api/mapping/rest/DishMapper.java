package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.DishReqDto;
import net.vinpos.api.dto.rest.response.DishResDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface DishMapper {

  Dish dto2Model(DishReqDto dto);

  DishResDto model2Dto(Dish user);

  void updateModelFromDto(DishReqDto dto, @MappingTarget Dish dish);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
      source = "content",
      target = "items",
      defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<DishResDto> model2Dto(Page<Dish> page);
}
