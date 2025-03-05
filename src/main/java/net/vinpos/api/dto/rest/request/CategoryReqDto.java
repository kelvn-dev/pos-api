package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryReqDto {
  @NotBlank private String name;
}
