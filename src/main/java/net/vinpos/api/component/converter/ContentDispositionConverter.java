package net.vinpos.api.component.converter;

import net.vinqr.api.enums.ContentDisposition;
import net.vinqr.api.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;

public class ContentDispositionConverter implements Converter<String, ContentDisposition> {

  @Override
  public net.vinqr.api.enums.ContentDisposition convert(String source) {
    try {
      return ContentDisposition.valueOf(source.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid Content-Disposition");
    }
  }
}
