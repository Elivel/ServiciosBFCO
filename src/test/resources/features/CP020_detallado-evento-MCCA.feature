Feature: Validate that when performing the query for a date range,
  a table with the result of the generated query is displayed on the report screen.

  Scenario: download a report Detallado por evento MCCA
    Given open web browser in "http://f8sc00008/Reports/browse/"
    And  queries the directory "mastercard"
    And queries the directory "Billing"
    And queries the report "Detallado por evento MCCA"
    When applies the necessary parameters
    And "Fecha" = ""
    And "Evento" = ""
    Then the report should be downloaded successfully
