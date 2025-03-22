package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class OrderReqDto {
  @NotBlank private String sessionId;
  @NotNull private Integer tableNumber;
  @NotEmpty private Set<OrderItemReqDto> orderItems;
}
