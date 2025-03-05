package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DishReqDto {
  @NotBlank private String name;
  @NotNull private Double price;
  private String description;
  private Boolean isShown;
  private UUID categoryId;
}
