Feature: Validate that when performing the query for a date range,
  a table with the result of the generated query is displayed on the report screen.

  Scenario: download a report CLEARING
    Given open web browser in "http://f8sc00008/Reports/browse/"
    And queries the directory "Mastercard"
    And queries the directory "Daily operation"
    And queries the report "CLEARING"
    When applies the necessary parameters
    And "Fecha" = ""
    Then the report should be downloaded successfully
