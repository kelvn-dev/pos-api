package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.FloorReqDto;
import net.vinpos.api.dto.rest.response.FloorResDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.model.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface FloorMapper {

  Floor dto2Model(FloorReqDto dto);

  FloorResDto model2Dto(Floor user);

  void updateModelFromDto(FloorReqDto dto, @MappingTarget Floor dish);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
      source = "content",
      target = "items",
      defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<FloorResDto> model2Dto(Page<Floor> page);
}
