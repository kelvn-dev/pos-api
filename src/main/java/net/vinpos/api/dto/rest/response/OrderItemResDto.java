package net.vinpos.api.dto.rest.response;

import lombok.Data;

@Data
public class OrderItemResDto {
  private Integer quantity;
  private String dishName;
  private Double dishPrice;
  private String note;
}
