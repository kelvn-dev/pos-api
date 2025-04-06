package net.vinpos.api.dto.rest.response;

import java.util.UUID;
import lombok.Data;
import net.vinpos.api.enums.TableStatus;

@Data
public class TableResDto {
  private UUID id;
  private Integer number;
  private String description;
  private TableStatus status;
  private String floorName;
}
