Feature: Expense report printing (legacy behavior)

  Scenario: Print expense report with all expenses
    Given I have a breakfast of 100 and a dinner of 200 and a car rental of 300
    When I print the expense report
    Then the report should contain all expenses

  Scenario: Print expense report with breakfast only
    Given I have a breakfast of 100
    When I print the expense report
    Then the report should contain only breakfast