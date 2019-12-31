package com.transaction.service.exception;

/**
 * Currency can not be converted due to error or currency conversion not supported.
 */
public class CurrencyConversionException extends Exception {

  public CurrencyConversionException(final String msg) {
    super(msg);
  }
}
