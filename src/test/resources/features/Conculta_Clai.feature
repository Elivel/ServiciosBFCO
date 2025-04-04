Feature: Login and Download Report
  Background: open page enter username and password
     Given the user accesses the login page

  Scenario: download a report consulta_Clai
    Given queries the directory "Conciliacion_Liquidacion"
    And queries the report "CONSULTA_CLAI"
    When applies the necessary parameters
    And "Tarjeta" = ""
    And "NroAutorizacion" = "33909"
    And "NroAutorOriginal" = ""
    And "FechaTrx" = ""
    Then the report should be downloaded successfully

  Scenario: download a report CLEARING
    Given queries the directory "Mastercard"
    And queries the directory "Daily operation"
    And queries the report "CLEARING"
    When applies the necessary parameters
    And "Fecha" = ""
    Then the report should be downloaded successfully

  Scenario: download a report Detallado por evento MCCA
    Given queries the directory "Mastercard"
    And queries the directory "Billing"
    And queries the report "Detallado por evento MCCA"
    When applies the necessary parameters
    And "Fecha" = ""
    And "Evento" = ""
    Then the report should be downloaded successfully

