Feature: Sign Up
  User registers to the system

  @register-feature
  Scenario Outline: User can successfully sign up with valid details
    Given I am on the sign up screen
    When I input username <username>
    And I input password <password>
    And I input password <repassword>
    And I click on the register button
    Then I should see the login screen

    Examples:
      | username | password | repassword |
      | galaxy       | password       | password |

  @signUp-feature
  Scenario Outline: User provides an invalid details or a field empty
    Given I am on the sign up screen
    When I input username <username>
    And I input password <password>
    And I input password <repassword>
    And I click on the register button
    Then I should see an error on the <view>

    Examples:
      | username | password | repassword | view |
      | galaxy       | password       | password | view |

  @signUp-feature
  Scenario Outline: User provides an existing email
    Given I am on the sign up screen
    When I input username <username>
    And I input password <password>
    And I input password <repassword>
    And I click on the register button
    Then I should <see> auth error

    Examples:
      | username | password | repassword | see                    |
      | galaxy       |  password       | password | true           |
      | galaxy1       | password       | password | false          |
      | galaxy1       | password1      | password1 | true           |