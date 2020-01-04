package com.bank.uat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpStatus;

public class DoneTransactionStepDefinitions {

  private double aliBalanceBeforeTransaction;
  private double goetheBalanceBeforeTransaction;
  private double revolutBalanceBeforeTransaction;
  private final BankHttpClient bankHttpClient = new BankHttpClient();


  @Given("Goethe and Ali have an account in Homework Bank")
  public void checkGeotheAndAliAccount() throws Exception {
    aliBalanceBeforeTransaction = bankHttpClient
        .getBalance("aliilyas", "ali786", "DE75512108001245126199");
    assertEquals(6564.45, aliBalanceBeforeTransaction, 0);

    goetheBalanceBeforeTransaction = bankHttpClient
        .getBalance("goethe", "faust123", "DE12500105170648489890");
    assertEquals(10000000000D, goetheBalanceBeforeTransaction, 0);
  }

  @When("Goethe creates a transaction of (.*) EUR for Ali")
  public void goetheCreatesTransaction(final String amountString) throws Exception {
    Double amount = Double.parseDouble(amountString);
    final int actualStatus = bankHttpClient
        .createTransaction("goethe", "faust123", "DE75512108001245126199", "DE12500105170648489890",
            amount, "paying back to ali", "123456789", "2020-12-31");
    assertEquals(HttpStatus.SC_CREATED, actualStatus);
  }

  @When("Goethe checks transaction status, he sees the status is Done")
  public void goetheChecksTransactionStatus() throws Exception {
    String status = bankHttpClient.checkFirstTransactionStatus("DE12500105170648489890");
    assertEquals("DONE", status);
  }

  @Then("^Ali's balance in account has been topped off with (.*) EUR$")
  public void checkingSuccessfulTransaction(final String amountStr) throws Exception {
    TimeUnit.SECONDS.sleep(1);
    Double amount = Double.parseDouble(amountStr);
    double aliBalanceAfterTransaction = bankHttpClient
        .getBalance("aliilyas", "ali786", "DE75512108001245126199");

    assertEquals(aliBalanceBeforeTransaction + amount, aliBalanceAfterTransaction, 0);

    double goetheBalanceAfterTransaction = bankHttpClient
        .getBalance("goethe", "faust123", "DE12500105170648489890");
    assertEquals(goetheBalanceBeforeTransaction - amount, goetheBalanceAfterTransaction, 0);
  }

  @Given("^Revolut wants to transfer (.*) EUR to an Iban not in HomeWork Bank$")
  public void checkRevolutHasEnoughCredit(final String amountString) throws Exception {
    double amount = Double.parseDouble(amountString);
    revolutBalanceBeforeTransaction = bankHttpClient
        .getBalance("revolut", "homework", "DE27100777770209299700");
    assertTrue(revolutBalanceBeforeTransaction > amount);
  }

  @When("Revolut creates a transaction of (.*) EUR for someone")
  public void revolutSuccessfulTransaction(final String amountString) throws Exception {
    Double amount = Double.parseDouble(amountString);
    int status = bankHttpClient
        .createTransaction("revolut", "homework", "DE34957931710796967291", "DE27100777770209299700",
            amount,"paying to someone from other bank", "1234523", "2020-12-31");
    assertEquals(HttpStatus.SC_CREATED, status);
  }

  @Then("^Revolut's balance in account has been reduced by (.*) EUR$")
  public void checkSuccessfulToOtherBankTransaction(final String amountString) throws Exception {
    TimeUnit.SECONDS.sleep(1);
    double amount = Double.parseDouble(amountString);
    double revolutAfterTransactionAmount = bankHttpClient
        .getBalance("revolut", "homework", "DE27100777770209299700");
    assertEquals(revolutBalanceBeforeTransaction - amount, revolutAfterTransactionAmount, 0);
  }
}
