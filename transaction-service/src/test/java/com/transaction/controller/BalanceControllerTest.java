package com.transaction.controller;

import static com.customer.portal.db.tables.CustomerAccount.CUSTOMER_ACCOUNT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.transaction.model.CheckBalanceRequest;
import com.transaction.service.ValidationTransactionService;
import com.transaction.service.impl.ValidationTransactionImpl;
import javax.ws.rs.core.Response;
import org.jooq.Record;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BalanceControllerTest {

  private BalanceController balanceController;

  @Before
  public void setUp() {
    ValidationTransactionService validationTransactionService = new ValidationTransactionImpl();
    balanceController = new BalanceController(validationTransactionService);
  }

  @Test
  public void checkValidRequestForBalanceTest() {
    final CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
    checkBalanceRequest.setPin("none");
    checkBalanceRequest.setUsername("someone");
    checkBalanceRequest.setIban("DE14601765707550691710");

    String isValidActual = balanceController.isValidRequestBody(checkBalanceRequest);
    assertEquals("true", isValidActual);
  }

  @Test
  public void checkInValidRequestForBalanceTest() {
    final CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
    checkBalanceRequest.setPin(null);
    checkBalanceRequest.setUsername("someone");
    checkBalanceRequest.setIban("DE14601765707550691710");

    String isValidActual = balanceController.isValidRequestBody(checkBalanceRequest);
    assertEquals("Password can not be null or empty.", isValidActual);
  }

  @Test
  public void checkInValidIbanForBalanceTest() {
    final CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
    checkBalanceRequest.setPin("noone");
    checkBalanceRequest.setUsername("someone");
    checkBalanceRequest.setIban("wrongIban");

    String isValidActual = balanceController.isValidRequestBody(checkBalanceRequest);
    assertEquals("Not a valid German Iban.", isValidActual);
  }

  @Test
  public void checkBalanceResponseTest() {
    Record record = mock(Record.class);

    when(record.get(CUSTOMER_ACCOUNT.USER_NAME)).thenReturn("noone");
    when(record.get(CUSTOMER_ACCOUNT.BALANCE)).thenReturn(100D);
    when(record.get(CUSTOMER_ACCOUNT.CURRENCY_TYPE)).thenReturn("eur");

    Response response = balanceController.generateOkResponse(record);
    assertEquals(200, response.getStatus());
  }

}