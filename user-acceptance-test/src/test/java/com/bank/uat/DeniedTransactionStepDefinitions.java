package com.bank.uat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpStatus;

public class DeniedTransactionStepDefinitions {

  private final BankHttpClient bankHttpClient = new BankHttpClient();

  @Given("Poor guy wants to make transaction")
  public void poorGuyWantsToMakeTransaction() throws Exception {
    double balance = bankHttpClient
        .getBalance("poorguy", "lessmoney", "DE02273209963801668468");
    assertTrue(balance > 0);
  }

  @When("^He makes a transaction of (.*) EUR$")
  public void poorGuyMakesTransaction(final String amountString) throws Exception
  {
    Double amount = Double.parseDouble(amountString);
    final int actualStatus = bankHttpClient
        .createTransaction("poorguy", "lessmoney", "DE14601765707550691710", "DE02273209963801668468",
            amount, "paying bills", "123456789", "2020-12-31");
    assertEquals(HttpStatus.SC_CREATED, actualStatus);
  }

  @Then("^Transaction is (.*) due to lack of funds$")
  public void verifyTransactionDenied(final String expectedStatus) throws Exception {
    TimeUnit.SECONDS.sleep(1);
    String status = bankHttpClient.checkFirstTransactionStatus("DE02273209963801668468");
    assertEquals(expectedStatus, status);
  }

}
