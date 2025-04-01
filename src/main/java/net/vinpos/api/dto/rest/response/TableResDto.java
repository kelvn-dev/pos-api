package net.vinpos.api.dto.rest.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import net.vinpos.api.enums.TableStatus;

import java.util.UUID;

@Data
public class TableResDto {
  private UUID id;
  private Integer number;
  private String description;
  private TableStatus status;
  private String floorName;
}
