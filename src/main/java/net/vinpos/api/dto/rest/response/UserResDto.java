package net.vinpos.api.dto.rest.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResDto {
  private String id;
  private String nickname;
  private String email;
  private String avatar;
  private String languageCode;

  private String memberShip;
  private int quantityQrLeft;
  private int storedQrQuantity;
  private Timestamp membershipExpiryDate;
}
