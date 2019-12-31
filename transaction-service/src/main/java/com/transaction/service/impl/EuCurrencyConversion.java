package com.transaction.service.impl;


import static com.transaction.service.CurrencyFedEx.fedExMap;

import com.transaction.service.CurrencyConversion;
import com.transaction.service.exception.CurrencyConversionException;
import com.transaction.service.CurrencyFedEx;
import com.transaction.service.exception.CurrencyFedExNotFoundException;

/**
 * Currency Conversion from USD to EURO and Vice Versa. Other currencies are not supported yet.
 */
public class EuCurrencyConversion implements CurrencyConversion {


  @Override
  public CurrencyFedEx getForeignExchange(Integer currencyCode)
      throws CurrencyFedExNotFoundException {
    if (!fedExMap.containsKey(currencyCode)) {
      throw new CurrencyFedExNotFoundException("Currency Foreign Exchange information not found.");
    }
    return fedExMap.get(currencyCode);
  }

  @Override
  public long convertCurrency(long currencyAmount, int currencyCurrentCode,
      int currencyCodeConvertTo) throws CurrencyConversionException {
    if (!fedExMap.containsKey(currencyCurrentCode)) {
      throw new CurrencyConversionException("Currency conversion is not supported yet, sorry.");
    }

    return 0;
  }


}
