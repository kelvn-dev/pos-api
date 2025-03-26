package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderItemReqDto {
  @NotNull private Integer quantity;
  @NotNull private UUID dishId;
  private String note;
}
