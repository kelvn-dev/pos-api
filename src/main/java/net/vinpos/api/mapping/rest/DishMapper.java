package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.DishReqDto;
import net.vinpos.api.dto.rest.response.DishResDto;
import net.vinpos.api.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DishMapper {

  Dish dto2Model(DishReqDto dto);

  DishResDto model2Dto(Dish user);

  void updateModelFromDto(DishReqDto dto, @MappingTarget Dish dish);
}
