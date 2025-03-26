package net.vinpos.api.mapping.rest;

import java.util.List;
import net.vinpos.api.dto.rest.request.OrderReqDto;
import net.vinpos.api.dto.rest.response.OrderResDto;
import net.vinpos.api.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order dto2Model(OrderReqDto dto);

  OrderResDto model2Dto(Order model);

  List<OrderResDto> model2Dto(List<Order> model);
}
