package net.vinpos.api.exception;

public class ForbiddenException extends VinposException {

  public ForbiddenException() {}

  public ForbiddenException(String message) {
    super(message);
  }
}
