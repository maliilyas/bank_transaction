package com.transaction.service.exception;

/**
 * Currency foreign exchange not found for given currency.
 */
public class CurrencyFedExNotFoundException extends Exception {

  public CurrencyFedExNotFoundException(final String msg) {
    super(msg);
  }

}
