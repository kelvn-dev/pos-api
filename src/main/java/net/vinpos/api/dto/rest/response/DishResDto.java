package net.vinpos.api.dto.rest.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DishResDto {
  private String name;
  private Double price;
  private String description;
  private Boolean isShown;
}
