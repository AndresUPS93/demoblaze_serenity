@compra
Feature: Purchase flow on DemoBlaze
  As a DemoBlaze user
  I want to add products to the cart and complete a purchase
  So that I can verify the E2E flow works correctly

  Scenario: Successful purchase of two products
    Given the user opens the DemoBlaze homepage
    When adds the product "Samsung galaxy s6" to the cart
    And goes back to home and adds the product "Nokia lumia 1520" to the cart
    And navigates to the shopping cart
    Then the cart should display 2 products
    When completes the purchase form with the following data:
      | name    | Juan Perez       |
      | card    | 1234567890123456 |
      | month   | 6                |
      | year    | 2026             |
      | country | Colombia         |
      | city    | Bogota           |
    And confirms the purchase
    Then the purchase should be completed successfully
