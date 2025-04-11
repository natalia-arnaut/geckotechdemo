Feature: It is possible to search recipes by name

  Background:
    Given I add a recipe "Pancake"
    And I add a recipe "Salad"
    And I add a recipe "French Pancake"

  Scenario: find a recipe
    When I search for recipe "Pancake"
    Then the response should contain recipes:
      | Pancake        |
      | French Pancake |
