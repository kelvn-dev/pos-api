package net.vinpos.api.dto.rest.response;

import java.util.UUID;
import lombok.Data;

@Data
public class DishResDto {
  private UUID id;
  private String name;
  private Double price;
  private String description;
  private Boolean isShown;
  private String image;
}
