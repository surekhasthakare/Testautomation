#Author: Surekha Thakate
@LatestExchangeRates
Feature: Validate API's for Latest Exhcange Rates

  Scenario: Validate status code for latest exchange rate for valid data
    Given Rest API is up and running
    And I set url "/latest"
    When I hit the api with GET request
    Then API return success status code as 200

  Scenario: Validate the success response for latest exchange rate for valid data
    Given Rest API is up and running
    And I set url "/latest"
    When I hit the api with GET request
    Then API return success status code as 200
    And base should be "EUR"

  Scenario: Validate status code for Latest exchange rate for valid data of base and symbols
    Given Rest API is up and running
    When I perform GET operation with Base and Symbols
      | base | symbols |
      | EUR  | GBP     |
    Then API return success status code as 200

  Scenario: Validate response for Latest exchange rate for valid data of base and symbols
    Given Rest API is up and running
    When I perform GET operation with Base and Symbols
      | base | symbol |
      | EUR  | GBP    |
    Then API return success status code as 200
    And validate the following response
      | base | rates |
      | EUR  | GBP   |
    And validate the Content Type as "application/json"

  Scenario: Validate status code for when incorrect or incomplete URL is provided
    Given Rest API is up and running
    And I set url "/errorrequest"
    When I hit the api with GET request
    Then API return success status code as 400
    And error message "time data 'errorrequest' does not match format '%Y-%m-%d'"
