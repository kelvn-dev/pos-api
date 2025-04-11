package net.vinpos.api.mapping.rest;

import java.util.List;
import net.vinpos.api.dto.rest.request.OrderReqDto;
import net.vinpos.api.dto.rest.response.OrderResDto;
import net.vinpos.api.dto.rest.response.PageResDto;
import net.vinpos.api.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order dto2Model(OrderReqDto dto);

  @Mapping(target = "createdAt", expression = "java(formatTimestamp(model.getCreatedAt()))")
  OrderResDto model2Dto(Order model);

  List<OrderResDto> model2Dto(List<Order> model);

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "number", target = "pageIndex")
  @Mapping(
      source = "content",
      target = "items",
      defaultExpression = "java(java.util.Collections.emptyList())")
  PageResDto<OrderResDto> model2Dto(Page<Order> page);

  // Hàm hỗ trợ định dạng timestamp
  default String formatTimestamp(long epochSeconds) {
    return java.time.format.DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
        .format(
            java.time.Instant.ofEpochSecond(epochSeconds)
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
  }
}
