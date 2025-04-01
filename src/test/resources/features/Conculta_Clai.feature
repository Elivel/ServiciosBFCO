Feature: Login and Download Report
  Background: open page enter username and password
    Given the user accesses the login page
    Given enters the username "user" and the password "password"
    When the user views the reports page


  Scenario: download a report consulta_Clai
    Given queries the report "report_name"
    When applies the necessary parameters
    Then the report should be downloaded successfully

  Scenario: download a report xxx
    Given queries the report "report_name"
    When applies the necessary parameters
    Then the report should be downloaded successfully

