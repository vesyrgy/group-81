@S4 @framework
Feature: Suspend the game
    As a player
    I want to be able to suspend the game
    So that I can pause and do something else

  @S4.1
  Scenario: Suspend the game
    Given the game has started
    When the user presses the "Stop" button
    Then then all moves from ghosts and the player are suspended