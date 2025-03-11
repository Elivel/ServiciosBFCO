# Folder: Conciliacion Liquidacion
#

Feature: Validate that when performing the query for a date range,
  a table with the result of the generated query is displayed on the report screen.

  Scenario: Generate report with default parameters
    Given a valid configuration file 'folder/consultaCLAI'
    When the scheduled job is executed 'ConsultaCLAITuple'
    Then the report must be generated correctly and saved as a CSV file 'temp/ConsultaCLAI_errors'
