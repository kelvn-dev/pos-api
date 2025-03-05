package net.vinpos.api.config;

import lombok.RequiredArgsConstructor;
import net.vinpos.api.component.converter.AclConverter;
import net.vinpos.api.component.converter.ContentDispositionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new ContentDispositionConverter());
    registry.addConverter(new AclConverter());
    WebMvcConfigurer.super.addFormatters(registry);
  }
}
