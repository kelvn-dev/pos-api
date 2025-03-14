package net.vinpos.api.sqli;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
  private static final SecureRandom secureRandom = new SecureRandom();

  public static String generateToken() {
    byte[] randomBytes = new byte[64];
    secureRandom.nextBytes(randomBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
  }
}
