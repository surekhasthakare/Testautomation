#Author: Surekha Thakate
@HistoricalExchangeRates
Feature: Validate API for Historical Exchange rates

  Scenario: Validate success status code and response for historical exchange rate with valid date
    Given Rest API is up and running
    And I set url "/2020-05-15"
    When I hit the api with GET request
    Then API return success status code as 200
    And base should be "EUR"
    And USD exchange Rate is "1.0798"
    And date is "2020-05-15"

  Scenario: Validate the status code for when future date is provided
    Given Rest API is up and running
    And I set url "/2021-01-20"
    When I hit the api with GET request
    Then API return success status code as 200
    And base should be "EUR"
    And API returns the exchange rate for current date "2020-12-08"

  Scenario: Validate status code when incorrect date format is passed as parameter
    Given Rest API is up and running
    And I set url "/15-05-2020"
    When I hit the api with GET request
    Then API return success status code as 400
    And error message "time data '15-05-2020' does not match format '%Y-%m-%d'"
    
