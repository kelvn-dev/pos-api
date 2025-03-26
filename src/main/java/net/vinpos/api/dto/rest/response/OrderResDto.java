package net.vinpos.api.dto.rest.response;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import net.vinpos.api.enums.OrderStatus;

@Data
public class OrderResDto {
  private UUID id;
  private Integer tableNumber;
  private Set<OrderItemResDto> orderItems;
  private OrderStatus status;
}
