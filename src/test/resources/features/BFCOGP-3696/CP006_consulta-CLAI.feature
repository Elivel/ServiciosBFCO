# Folder: Conciliacion Liquidacion
#

Feature: Validate that when performing the query for a date range,
  a table with the result of the generated query is displayed on the report screen.

  Scenario: Generate report with default parameters
    Given a valid configuration
    And file="C:\Users\elvelasquezl\Downloads\CONSULTA_CLAI.csv"
    And Username="appcertifica"
    And Userpass="qacertifica01"
    And url="jdbc:sqlserver://10.120.18.223;encrypt=false;database=master;integratedSecurity=false;"
    When the scheduled job is executed
    Then the report must be generated correctly and saved as a CSV file "C:\Users\elvelasquezl\Documents\ServicioBFCO"

