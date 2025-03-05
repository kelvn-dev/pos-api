package net.vinpos.api.dto.rest.response;

import java.util.UUID;
import lombok.Data;

@Data
public class CategoryResDto {
  private UUID id;
  private String name;
}
