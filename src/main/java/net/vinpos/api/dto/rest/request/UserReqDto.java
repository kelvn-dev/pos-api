package net.vinpos.api.dto.rest.request;

import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class UserReqDto {
  @NotNull private String avatar;
  @NotNull private String nickname;
  private String languageCode;
  private Boolean isMember;

  private Timestamp membershipExpiryDate;
}
