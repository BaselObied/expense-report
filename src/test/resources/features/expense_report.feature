Feature: Expense report printing (legacy behavior)
Feature: Expense report printing (legacy behavior)

  Scenario: Print expense report with breakfast, dinner and car rental
    Given I have a breakfast of 100 and a dinner of 200 and a car rental of 300
    When I print the expense report
    Then the report should be printed