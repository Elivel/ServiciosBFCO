Feature: Login and Download Report

  Scenario: Login, view reports, query, and download a report
    Given the user accesses the login page
    Given enters the username "user" and the password "password"
    When the user views the reports page
    When queries the report "report_name"
    When applies the necessary parameters
    Then the report should be downloaded successfully