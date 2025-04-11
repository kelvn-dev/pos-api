package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.TableReqDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.dto.rest.response.TableResDto;
import net.vinpos.api.model.TableEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TableMapper {
  TableEntity dto2Model(TableReqDto dto);

  @Mapping(source = "floor.name", target = "floorName")
  TableResDto model2Dto(TableEntity table);

  void updateModelFromDto(TableReqDto dto, @MappingTarget TableEntity table);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
      source = "content",
      target = "items",
      defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<TableResDto> model2Dto(Page<TableEntity> page);
}
