@search @smoke
@browserWeb

Feature: Web search Scenario

  Scenario: User should able to search
  Given the user navigates to "Elektronik" to "Bilgisayar/Tablet" to "Tablet"
  Then search result should be "Tablet Fiyatları ve Modelleri"
  When select title "markalar" filter "Apple"
  Then selected filter should be 1
  And select title "ekran boyutu" filter "13,2 inç"
  Then selected filter should be 2
  And selected filter name should be same
    | 13,2 inç    |
    | Apple       |
  And click highest price product
  Then verify open product detail page
  When save product information
  And click add cart
  When go to cart
  Then cart product count should be 1
  Then verify open cart
  And cart price should be same
