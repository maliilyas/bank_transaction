package com.transaction.service.impl;

import static com.transaction.db.DbUtil.fetchCustomer;

import com.transaction.service.ValidationTransactionService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.checkdigit.IBANCheckDigit;
import org.jooq.Record;
import org.jvnet.hk2.annotations.Service;

@Service
@Slf4j
public class ValidationTransactionImpl implements ValidationTransactionService {

  @Override
  public boolean isValidCustomer(String username, String pin, String fromIban) {
    List<Record> customer = fetchCustomer(username, pin, fromIban);
    return !customer.isEmpty() && customer.size() == 1;
  }

  @Override
  public boolean isValidToIban(String toIBan) {
    return new IBANCheckDigit().isValid(toIBan);
  }

  @Override
  public boolean isValidDate(Date transactionDate) {

    boolean isValidDate = false;

    Date now = new Date();
    if (transactionDate.after(now)) {
      isValidDate = true;
    }

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String nowDateFormat = dateFormat.format(now);
    String transactionDateFormat = dateFormat.format(transactionDate);

    if (transactionDateFormat.equals(nowDateFormat)) {
      isValidDate = true;
    }
    return isValidDate;
  }
}
