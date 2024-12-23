@Api
Feature: Api Scenarios

  Scenario: Retrieve booking
    Given get booking ID index 0
    When I send GET request to retrieve the booking
    Then I should get the booking details successfully

  Scenario: Create new booking
    Given I have booking details for a new customer
    When I send POST request to create booking
    Then the booking should be created successfully

  Scenario: Update booking
    Given get booking ID index 0
    When I have authentication token
    And I have updated booking details
    When I send PUT request to update the booking
    Then the booking should be updated successfully

  Scenario: Partial update booking
    Given I have authentication token
    When get booking ID index 0
    And I have partial booking details to update
    And I send PATCH request to update the booking
    Then the booking should be partially updated successfully

  Scenario: Delete booking
    Given I have authentication token
    When get booking ID index 0
    And send DELETE request to remove the booking
    Then verify status code 201

  Scenario: Get all booking IDs
    When GET request to retrieve all booking IDs
    Then I should receive a list of booking IDs


  Scenario: Health Check
    When GET request to ping the API
    Then verify status code 201
