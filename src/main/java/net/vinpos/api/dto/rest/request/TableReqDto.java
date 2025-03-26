package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Data;
import net.vinpos.api.enums.TableStatus;

@Data
public class TableReqDto {
  @NotBlank private Integer number;
  private String description;
  private TableStatus status;
  private UUID floorId;
}
