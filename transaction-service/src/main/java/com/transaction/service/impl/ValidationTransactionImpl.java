package com.transaction.service.impl;

import static com.transaction.db.DbUtil.fetchCustomer;

import com.transaction.service.ValidationTransactionService;
import java.util.Date;
import java.util.List;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.jooq.Record;
import org.jvnet.hk2.annotations.Service;

@Service
public class ValidationTransactionImpl implements ValidationTransactionService {

  @Override
  public boolean isValidCustomer(String username, String pin, String fromIban) {
    List<Record> customer = fetchCustomer(username, pin, fromIban);
    return !customer.isEmpty() && customer.size() == 1;
  }

  @Override
  public boolean isValidToIban(String toIBan) {
    return  new IBANCheckDigit().isValid(toIBan);
  }

  @Override
  public boolean isValidDate(Date transactionTime) {
    return  !new Date().after(transactionTime);
  }
}
