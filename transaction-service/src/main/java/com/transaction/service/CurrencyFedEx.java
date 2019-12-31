package com.transaction.service;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Information for Foreign Exchange with respect to Unitied State Dollar (ISO-4217 -> 840)
 */
@AllArgsConstructor
@ToString
public class CurrencyFedEx {

  /**
   * The enum for ISO-4217 currency code.
   */
  public enum CurrencyCode {
    EUR(978),
    USD(840);

    public final int currencyCode;

    private CurrencyCode(final int currencyCode) {
      this.currencyCode = currencyCode;
    }
  }

  public  static final Map<String, CurrencyFedEx> fedExMap = new HashMap();
  static {
      fedExMap.put("EUR", new CurrencyFedEx(1.11F, 1.11F));
      fedExMap.put("USD", new CurrencyFedEx(0.90F, 0.90F));
  }

  private float selling;
  private float buying;
}