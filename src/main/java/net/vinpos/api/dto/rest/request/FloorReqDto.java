package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FloorReqDto {
  @NotBlank private String name;
}
