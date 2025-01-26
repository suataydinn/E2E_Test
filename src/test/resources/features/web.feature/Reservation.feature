@reservation @regression
@browserWeb
Feature: This feature contains create a new reservation

  @WEB-004
  Scenario: User should be able to see provision fee when search Turkey location
    When search "Sabiha Gökçen Havalimanı"
    And click find button
    Then verify open search result page
    And verify provision filter and car card provision info are seen
    And Start devtools Listeners "GET" method for response "search-api/search"
    And Select vehicle in row 1
    Then check provision fee BE and UI
    Then verify provision info is seen
    And click rent now button
    And enter guest TCKN driver information
    And click rent now button
    And enter payment method
    And click rent now button
    And send sms key
    And click reservation detail button
    Then the user should be able to make reservation
    Then verify provision info is seen
    And verify provision value same BE and UI


  @WEB-005 @smoke
  Scenario: login user rent a car by selecting a different address that they have registered. They should see that all the information about the car is the same on the reservation details page and in the API
    When click the register
    Then verify open Sign in Sign Up page
    When user enter correct information for login
    Then user should be able to login
    When search "Sabiha Gökçen Uluslararası Havalimanı (SAW)"
    And click find button
    And Select vehicle in row 1
    And save data car detail URL and UI
    And click rent now button
    Then verify billing count 4
    And select billing row 3
    And save billing row 3
    And click rent now button
    And enter payment method
    And click rent now button
    And send sms key
    And click reservation detail button
    Then the user should be able to make login reservation
    And verify match reservation detail and API
    Then match and verify car detail and reservation detail
    And verify match reservation detail and DB
