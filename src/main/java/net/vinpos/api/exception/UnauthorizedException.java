package net.vinpos.api.exception;

public class UnauthorizedException extends VinposException {

  public UnauthorizedException() {}

  public UnauthorizedException(String message) {
    super(message);
  }
}
