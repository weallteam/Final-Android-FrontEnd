Feature: Search
  User search in the system

  @search-feature
  Scenario Outline: User can provide an empty field
    Given I am on the dashboard screen
    When I enter anything <search>
    And I click on the search button
    Then I receive a field required message

    Examples:
      | search            |
      | galaxy@example.com |