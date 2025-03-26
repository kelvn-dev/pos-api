package net.vinpos.api.dto.rest.response;

import jakarta.validation.constraints.NotBlank;
import net.vinpos.api.enums.TableStatus;

public class TableResDto {
  @NotBlank private Integer number;
  private String description;
  private TableStatus status;
}
