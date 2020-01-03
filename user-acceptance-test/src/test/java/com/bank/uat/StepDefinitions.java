package com.bank.uat;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpStatus;

/**
 * VALUES (1, 'aliilyas', 'ma420', 'DE75512108001245126199', 6564.45, 'EUR') ;
 *
 * INSERT INTO customer_account VALUES (2, 'goethe', 'faust123', 'DE12500105170648489890',
 * 10000000000, 'EUR') ;
 *
 * INSERT INTO customer_account VALUES (3, 'homework', 'revolut', 'DE27100777770209299700',
 * 9000000000000, 'EUR')
 */
public class StepDefinitions {

  private double aliBalanceBeforeTransaction;
  private double goetheBalanceBeforeTransaction;
  private BankHttpClient bankHttpClient = new BankHttpClient();


  @Given("Goethe and Ali have an account in Homework Bank")
  public void checkGeotheAndAliAccount() throws Exception {
    aliBalanceBeforeTransaction = bankHttpClient
        .getBalance("aliilyas", "ma420", "DE75512108001245126199");
    assertEquals(6564.45, aliBalanceBeforeTransaction, 0);

    goetheBalanceBeforeTransaction = bankHttpClient
        .getBalance("goethe", "faust123", "DE12500105170648489890");
    assertEquals(10000000000D, goetheBalanceBeforeTransaction, 0);
  }

  @When("Goethe creates a transaction of 1050 EUR for Ali")
  public void goetheCreatesTransaction() throws Exception {
    final int actualStatus = bankHttpClient
        .createTransaction("goethe", "faust123", "DE75512108001245126199", "DE12500105170648489890",
            1050D, "paying back to ali", "123456789", "2020-12-31");
    assertEquals(HttpStatus.SC_CREATED, actualStatus);
  }

  @When("Goethe checks transaction status, he sees the status is Done")
  public void goetheChecksTransactionStatus() throws Exception {
    String status = bankHttpClient.checkFirstTransactionStatus("DE12500105170648489890");
    assertEquals("DONE", status);
  }

  @Then("^Ali's balance in account has been topped off with (.*) EUR$")
  public void checkingSuccessfulTransaction(String amountStr) throws Exception {
    TimeUnit.SECONDS.sleep(1);
    Double amount = Double.parseDouble(amountStr);
    double aliBalanceAfterTransaction = bankHttpClient
        .getBalance("aliilyas", "ma420", "DE75512108001245126199");

    assertEquals(aliBalanceBeforeTransaction + amount, aliBalanceAfterTransaction, 0);

    double goetheBalanceAfterTransaction = bankHttpClient
        .getBalance("goethe", "faust123", "DE12500105170648489890");
    assertEquals(goetheBalanceBeforeTransaction - amount,  goetheBalanceAfterTransaction, 0);
  }
}
