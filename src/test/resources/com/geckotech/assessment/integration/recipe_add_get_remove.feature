Feature: It is possible to add, get and remove Recipe info

  Scenario: No recipes
    When I request recipe "Pancake" by id
    Then the response should be NOT FOUND
    When I request all recipes
    Then the response should be OK
    And the response should be empty list

  Scenario: Add and get and remove recipes
    Given I add a recipe "Pancake"
    And I add a recipe "Salad"
    When I request recipe "Pancake" by id
    Then the response should be one recipe "Pancake"
    When I request all recipes
    Then the response should contain recipes:
      | Pancake |
      | Salad   |
    When I remove recipe "Pancake"
    Then the response should be NO CONTENT
    When I request recipe "Pancake" by id
    Then the response should be NOT FOUND
