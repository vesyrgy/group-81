@S2 @multilevel
Feature: Multi-level Game
  As a player
  I want to be able to play up to three additional levels
  So that the game becomes more challenging

  @S5.1
  Scenario: Player wins the level
      Given the game has started
      And   the current level is less than four
      And   my Pacman is next to a square containing a pellet
      When  I have eaten the last pellet
      Then  I win the level
      And   the next level starts

  @S5.2
  Scenario: The player wins the Game
      Given the game has started
      And   the current level is four
      And   my Pacman is next to a square containing a pellet
      When  I have eaten the last pellet
      Then  I win the game

