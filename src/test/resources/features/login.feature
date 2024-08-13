@login
Feature: logging in to SauceDemo

  Scenario: login to sauce demo successfully
    Given the user is on the url
    When user enters a correct username
    Then user enters a correct password
    And user clicks on login button
    Then verify the user is logged in

