Feature: DoneTransaction

  Scenario: paying back borrowed money
    Given Goethe and Ali have an account in Homework Bank
    When  Goethe creates a transaction of 1050 EUR for Ali
    When  Goethe checks transaction status, he sees the status is Done
    Then  Ali's balance in account has been topped off with 1050 EUR

  Scenario: transaction to other bank
    Given Revolut wants to transfer 100 EUR to an Iban not in HomeWork Bank
    When  Revolut creates a transaction of 100 EUR for someone
    Then  Revolut's balance in account has been reduced by 100 EUR