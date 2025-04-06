package net.vinpos.api.mapping.rest;

import java.util.List;
import net.vinpos.api.dto.rest.request.OrderReqDto;
import net.vinpos.api.dto.rest.response.OrderResDto;
import net.vinpos.api.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order dto2Model(OrderReqDto dto);

  @Mapping(target = "createdAt", expression = "java(formatTimestamp(model.getCreatedAt()))")
  OrderResDto model2Dto(Order model);

  List<OrderResDto> model2Dto(List<Order> model);

  // Hàm hỗ trợ định dạng timestamp
  default String formatTimestamp(long epochSeconds) {
    return java.time.format.DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
            .format(java.time.Instant.ofEpochSecond(epochSeconds)
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime());
  }
}
