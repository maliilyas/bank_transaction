package com.transaction.service.impl;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.transaction.service.ValidationTransactionService;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class ValidationTransactionImplTest {
  private ValidationTransactionService validationTransactionService;

  @Before
  public void setUp() {
    validationTransactionService = new ValidationTransactionImpl();
  }

  @Test
  public void isValidIbanTest() {
    assertTrue(validationTransactionService.isValidToIban("DE18815774588533805761"));
  }

  @Test
  public void inValidIbanTest() {
    assertFalse(validationTransactionService.isValidToIban("fnafji3j391r31"));
  }

  @Test
  public void pastDateTest() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, -365);
    assertFalse(validationTransactionService.isValidDate( c.getTime() ));
  }

  @Test
  public void nowDateTest() {
    assertTrue(validationTransactionService.isValidDate(new Date()));
  }

  @Test
  public void futureDateTest() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, 365);
    assertTrue(validationTransactionService.isValidDate(c.getTime()));
  }



}