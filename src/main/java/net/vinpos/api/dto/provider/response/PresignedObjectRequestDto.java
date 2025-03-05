package net.vinpos.api.dto.provider.response;

import lombok.Data;

import java.util.Map;

@Data
public class PresignedObjectRequestDto {
  private String url;
  private long expiration;
  private Map<String, String> signedHeaders;
}
