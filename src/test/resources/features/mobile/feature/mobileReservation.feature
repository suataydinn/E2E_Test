@browserMobile @regression
@searchMobile @smokeMobile
Feature: Web Mobile search Scenario

  @MOBILE-001
  Scenario: Go back from the payment page and choose a more expensive vehicle, the total price should change in mobile view
    When search "Sabiha Gökçen Havalimanı"
    And sort by "En düşük fiyat"
    And save first car price
    And Select vehicle in row 1
    Then verify mobile price on car detail & search result
    And click mobile rent now button
    And enter guest TCKN driver information
    Then verify mobile price on car detail & search result
    And click mobile rent now button
    And enter payment method
    Then verify mobile price on car detail & search result
    When click back to previous page
    And click back to previous page
    And click back to previous page
    And sort by "En yüksek fiyat"
    And save first car price
    And Select vehicle in row 1 and verify "Yolcutest"
    Then verify mobile price on car detail & search result
    When click rent now button
    Then verify mobile price on car detail & search result
    When click rent now button
    And send sms key
    And click reservation detail button
    Then verify calculate price on reservation detail page
    Then verify mobile price on car detail & search result on reservation detail page
    Then the user should be able to make reservation in mobile view
    And verify match reservation detail and DB
