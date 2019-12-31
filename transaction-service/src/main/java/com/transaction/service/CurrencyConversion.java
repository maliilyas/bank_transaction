package com.transaction.service;

import com.transaction.service.exception.CurrencyConversionException;
import com.transaction.service.exception.CurrencyFedExNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service to provide currency conversion.
 */
public interface CurrencyConversion {

  /**
   * The information for selling and buying a particular currency.
   * @param currencyCode the ISO-4217 code of currency.
   * @return Foreign exchange information of provided currency.
   * @throws CurrencyFedExNotFoundException, if no foreign exchange is found.
   */
  CurrencyFedEx getForeignExchange(final Integer currencyCode) throws CurrencyFedExNotFoundException;

  /**
   * Converts the currency in provided type.
   * @param currencyAmount the amount of currency to be converted.
   * @param currencyCurrentCode the type of current currency.
   * @param currencyCodeConvertTo the type to be converted in.
   * @return
   * @throws CurrencyConversionException
   */
  long convertCurrency(final long currencyAmount, final int currencyCurrentCode, final int currencyCodeConvertTo) throws CurrencyConversionException;




}

