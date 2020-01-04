package com.transaction.service;

import java.util.Date;

public interface ValidationTransactionService {

  /**
   * Check whether Customer has an account in Homework Bank.
   *
   * @param username the username of the customer.
   * @param pin the password of the customer.
   * @param fromIban the iban of the customer.
   */
  boolean isValidCustomer(final String username, final String pin, final String fromIban);

  /**
   * Is the iban of receiver customer valid (Only German ibans are supported).
   *
   * @param toIBan the iban of a receiver
   */
  boolean isValidToIban(final String toIBan);

  /**
   * Checks whether the Date is valid. Now and future transactions are valid.
   *
   * @param transactionDate the transaction date.
   */
  boolean isValidDate(final Date transactionDate);

}
