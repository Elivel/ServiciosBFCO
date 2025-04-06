Feature: Validate that when performing the query for a date range,
  a table with the result of the generated query is displayed on the report screen.

  Scenario: download a report consulta_Clai
    Given open web browser in "http://f8sc00008/Reports/browse/"
    And queries the directory "Conciliacion_Liquidacion"
    And queries the report "CONSULTA_CLAI"
    When applies the necessary parameters
    And "Tarjeta" = ""
    And "NroAutorizacion" = "33909"
    And "NroAutorOriginal" = ""
    And "FechaTrx" = ""
    Then the report should be downloaded successfully


