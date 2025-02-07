 @regression
 @browserWeb
Feature: This feature contains create a new reservation

 # @WEB-001
  Scenario: Compare car price which send from BE with car detail price
    Given search "Sabiha Gökçen Havalimanı"
    When select date and time from calendar
    When click find button
    And Start devtools Listeners "GET" method for response "search-api/search"
    And Select vehicle in row 1
    Then Price sended from BE should be same Car detail price

#@WEB-002 @smoke @operation
  Scenario: Check payload request for search flow
  Given Start devtools Listeners "POST" method for response "point"
  When select "Türkiye" country
  And select "25" age
  And search "Dalaman Havalimanı"
  And click find button
  And verify open search result page
  Then get payload information and compare point information
  And Start devtools Listeners "POST" method for response "order-api/order"
  And Select vehicle in row 1
  Then get payload for order api on car detail
  And click rent now button
  Then get payload for item
  And enter guest TCKN driver information
  And click rent now button
  And Start devtools Listeners "POST" method for response "info"
  And enter payment method
  Then check payment info
  And check bin Number response

  #  @WEB-003 @operation
  Scenario: user enter driver info on information page for abroad country, tax identifier should be sent in billing end point
    When search "Madrid Barajas Uluslararası Havalimanı"
    And click find button
    And Select vehicle in row 1
    And click rent now button
    And enter abroad guest driver information
    And Start devtools Listeners "POST" method for response "billing"
    And click rent now button
    Then Tax identifier should be "22222222222"


