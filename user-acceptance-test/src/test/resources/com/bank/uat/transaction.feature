Feature: Transaction

  Scenario: paying back borrowed money
    Given Goethe and Ali have an account in Homework Bank
    When  Goethe creates a transaction of 1050 EUR for Ali
    When  Goethe checks transaction status, he sees the status is Done
    Then  Ali's balance in account has been topped off with 1050 EUR