Feature: Booking
  User Booking in the system

  @book-feature
  Scenario Outline: User can provide an empty field
    Given I am on the dashboard screen
    When I enter book service title <service>
    When I select service image <image>
    And I click on the book button
    Then The service is booked

    Examples:
      | service | image |
      | salon | salon.jpg |

  @book-feature
  Scenario Outline: User can provide an invalid value
    Given I am on the dashboard screen
    When I enter book service title <service>
    When I select service image <image>
    And I click on the book button
    Then I receive an invalid login message

    Examples:
      | service            | image  |
      | salon11 | salon.jpg |

