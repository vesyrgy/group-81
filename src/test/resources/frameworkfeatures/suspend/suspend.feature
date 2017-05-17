@S4 @framework
Feature: Suspend the game
  As a player
  I want to be able to suspend the game
  So that I can pause and do something else

  @S4.1
  Scenario: Suspend the game
    Given the game has been started
    When the user presses the stop button
    Then the game is suspended

  @S4.2
  Scenario: Restart the game
    Given the game has been suspended
    When the user presses the start button
    Then the game is resumed
