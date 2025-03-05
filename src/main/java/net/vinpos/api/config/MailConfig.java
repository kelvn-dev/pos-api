package net.vinpos.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailConfig {
  private String host;
  private String port;
  private String sender;
  private String subject;
  private String username;
  private String password;
}
