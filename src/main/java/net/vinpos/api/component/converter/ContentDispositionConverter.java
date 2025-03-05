package net.vinpos.api.component.converter;

import net.vinpos.api.enums.ContentDisposition;
import net.vinpos.api.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;

public class ContentDispositionConverter implements Converter<String, ContentDisposition> {

  @Override
  public ContentDisposition convert(String source) {
    try {
      return ContentDisposition.valueOf(source.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid Content-Disposition");
    }
  }
}
