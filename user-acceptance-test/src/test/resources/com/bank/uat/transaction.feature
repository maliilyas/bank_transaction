Feature: Transaction

  Scenario: paying back borrowed money
    Given Goethe owns Ali 1050 EUR and both have an account in Homework Bank
    When  Goethe creates a transaction for Ali to get money
    Then  Ali's balance in account has been topped off with 1050 EUR