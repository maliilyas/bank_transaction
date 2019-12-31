package com.bank.uat;

import io.cucumber.java.en.Given;

public class StepDefinitions {
  @Given("Goethe owns Ali {double} EUR and both have an account in Homework Bank")
  public void I_have_cukes_in_my_belly(double cukes) throws Throwable {
    System.out.println(cukes);
  }
}
