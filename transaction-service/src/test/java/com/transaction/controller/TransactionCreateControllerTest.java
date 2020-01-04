package com.transaction.controller;


import static org.junit.Assert.assertEquals;

import com.transaction.model.CreateTransactionRequest;
import com.transaction.service.ValidationTransactionService;
import com.transaction.service.impl.ValidationTransactionImpl;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class TransactionCreateControllerTest {

  private TransactionCreateController transactionCreateController;

  @Before
  public void setUp() {
    ValidationTransactionService validationTransactionService = new ValidationTransactionImpl();
    transactionCreateController = new TransactionCreateController(validationTransactionService);
  }

  @Test
  public void isValidRequestForTransactionCreationTest() {
    CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
    createTransactionRequest.setAmount(100D);
    createTransactionRequest.setFromIban("DE14601765707550691710");
    createTransactionRequest.setToIban("DE18815774588533805761");
    createTransactionRequest.setPin("none");
    createTransactionRequest.setUsername("anyone");

    // future date
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, 365);
    createTransactionRequest.setTransactionDate(c.getTime());
    createTransactionRequest.setTransactionMessage("nothing");
    createTransactionRequest.setTransactionReference("1234567");

    String isValid = transactionCreateController.isValidRequestBody(createTransactionRequest);
    assertEquals("true", isValid);
  }

  @Test
  public void sameIBanCreateTransactionTest() {
    CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
    createTransactionRequest.setAmount(100D);
    createTransactionRequest.setFromIban("DE18815774588533805761");
    createTransactionRequest.setToIban("DE18815774588533805761");
    createTransactionRequest.setPin("none");
    createTransactionRequest.setUsername("anyone");

    createTransactionRequest.setTransactionDate(new Date());
    createTransactionRequest.setTransactionMessage("nothing");
    createTransactionRequest.setTransactionReference("1234567");

    String isValid = transactionCreateController.isValidRequestBody(createTransactionRequest);
    assertEquals("self transaction is not allowed.", isValid);
  }
}