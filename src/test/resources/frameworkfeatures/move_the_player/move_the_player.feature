@S2 @framework
Feature: Start to play
  As a player
  I want to move my Pacman around on the board
  So that I can earn all points and win the game

  @S2.1
  Scenario: The player consumes
      Given the game has started
      And   my Pacman is next to a square containing a pellet
      When  I press an arrow key towards that square
      Then  my Pacman can move to that square
      And   I earn the points for the pellet
      And   the pellet disappears from that square

  @S2.2
  Scenario: The player moves on empty square
      Given the game has started
      And   my Pacman is next to an empty square
      When  I press an arrow key towards that square
      Then  my Pacman can move to that square
      And   my points remain the same

  @S2.3
  Scenario: The move fails
      Given the game has started
      And   my Pacman is next to a cell containing a wall
      When  I press an arrow key towards that cell
      Then  the move is not conducted

    @S2.4
    Scenario: The player dies
      Given the game has started
      And my Pacman is next to a cell containing a ghost
      When I press an arrow key towards that square
      Then my Pacman dies
      And the game is over

      @S2.5
      Scenario: The player wins
        Given the game has started
        And my Pacman is next to a square containing a pellet containing a pellet
        When I have eaten the last pellet
        Then I win the game
