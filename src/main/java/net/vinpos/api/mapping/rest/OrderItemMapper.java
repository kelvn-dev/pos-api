package net.vinpos.api.mapping.rest;

import net.vinpos.api.dto.rest.request.OrderItemReqDto;
import net.vinpos.api.model.Dish;
import net.vinpos.api.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

  @Mapping(source = "dish.name", target = "dishName")
  @Mapping(source = "dish.price", target = "dishPrice")
  OrderItem dto2Model(OrderItemReqDto dto, Dish dish);

  void updateModelFromDto(OrderItemReqDto dto, @MappingTarget OrderItem model);

  //  OrderResDto model2Dto(Order model);
  //
  //  List<OrderResDto> model2Dto(List<Order> model);
}
