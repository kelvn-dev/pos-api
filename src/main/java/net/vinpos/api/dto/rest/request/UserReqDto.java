package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserReqDto {
  @NotNull private String avatar;
  @NotNull private String nickname;
  private String languageCode;
  private Boolean isMember;

  private Timestamp membershipExpiryDate;
}
