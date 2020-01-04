Feature: DeniedTransaction

  Scenario: poor guy trying to make transaction but denied
    Given Poor guy wants to make transaction
    When He makes a transaction of 100 EUR
    Then Transaction is DENIED due to lack of funds