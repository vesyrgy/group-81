package nl.tudelft.jpacman.e2e.group81.move_the_player;

import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.group81.MyExtension;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * Step definitions for the Cucumber tests.
 * @author Lars Ysla
 */
@SuppressWarnings("PMD.TooManyMethods")
public class MoveThePlayerSteps {

    /**
     *  The TestObserver is used to detect whether or not a level was won.
     */
    private static class TestObserver implements Level.LevelObserver {
        private int levelsWon = 0;

        int getLevelsWon() {
            return levelsWon;
        }

        public void levelWon() {
            levelsWon++;
        }

        public void levelLost() {
        }
    }

    private Launcher launcher;
    private Game getGame() {
        return launcher.getGame();
    }
    private Player player;
    private Square square;
    private Square newSquare;
    private Direction whereToGo;
    private int score;
    private TestObserver observer;

    /**
     * Initialize a clean launcher and launch it
     * before every move_the_player test.
     */
    @Before("@move_the_player")
    public void setup() {
        //  use a map where the Pacman is next to a pellet, a wall, and an empty cell
        launcher = new MyExtension().withMapFile("/testMap1.txt");
        launcher.launch();
    }

    /**
     * Start a game and check that it is running.
     */
    @Given("^the game has started$")
    public void the_game_has_started() {
        // Register an observer, for knowing whether
        // the level was won or lost/
        observer = new TestObserver();
        getGame().getLevel().addObserver(observer);
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();

        //  Set the player and score variables
        player = getGame().getPlayers().get(0);
        score = player.getScore();
    }

    /**
     *  Make sure the player is set to go to the east,
     *  and check whether the east has a pellet.
     */
    @Given("^my Pacman is next to a square containing a pellet$")
    public void my_Pacman_is_next_to_a_square_containing_a_pellet() {
        // Get the player's square
        square = player.getSquare();
        //  Pick the direction of an empty square in textMap1.txt & set the destination square
        whereToGo = Direction.EAST;
        newSquare = square.getSquareAt(whereToGo);
        // Make sure the square does indeed contain a pellet
        assertThat(newSquare.getOccupants().get(0) instanceof Pellet).isTrue();

    }

    /**
     * Move the player in the direction it was set to move
     * by a @Given method.
     */
    @When("^I press an arrow key towards that square$")
    public void i_press_an_arrow_key_towards_that_square() {
        getGame().move(player, whereToGo);

    }

//    /**
//     * Check to see that the current square is the square
//     * to which we wanted to move.
//     */
//    @Then("^my Pacman can move to that square$")
//    public void my_Pacman_can_move_to_that_square() {
//        assertThat(player.getSquare()).isEqualTo(newSquare);
//    }

    /**
     * Check if the score has been incremented.
     */
    @Then("^I earn the points for the pellet$")
    public void i_earn_the_points_for_the_pellet() {
        assertThat(player.getScore() > score).isTrue();
    }

    /**
     * Check if the square to which the player has moved no longer
     * contains a pellet.
     */
    @Then("^the pellet disappears from that square$")
    public void the_pellet_disappears_from_that_square() {
        for (Unit occupant: player.getSquare().getOccupants()) {
            assertThat(occupant instanceof Pellet).isFalse();
        }
    }

    /**
     * Make sure the player is set to go to the west,
     * and check whether the west is an empty square.
     */
    @Given("^my Pacman is next to an empty square$")
    public void my_Pacman_is_next_to_an_empty_square() {
        square = player.getSquare();
        //  Pick the direrection of an empty square in textMap1.txt & set the destination square
        whereToGo = Direction.WEST;
        newSquare = square.getSquareAt(whereToGo);
        //  Check that the square is empty
        assertThat(newSquare.getOccupants().isEmpty()).isTrue();
    }

    /**
     * Check if the score is still the same.
     */
    @Then("^my points remain the same$")
    public void my_points_remain_the_same() {
        assertThat(player.getScore()).isEqualTo(score);
    }

    /**
     * Make sure the player is set to go to the north,
     * and check whether the north contains a wall.
     */
    @Given("^my Pacman is next to a cell containing a wall$")
    public void my_Pacman_is_next_to_a_cell_containing_a_wall() {
        square = player.getSquare();
        //  Pick the direrection of a wall in textMap1.txt & set the destination square
        whereToGo = Direction.NORTH;
        newSquare = square.getSquareAt(whereToGo);
        //  Check if the cell is a wall (assuming walls are the only inaccessible squares)
        assertThat(newSquare.isAccessibleTo(player)).isFalse();
    }

    /**
     * Move the player in the direction it was set to move
     * by a @Given method.
     */
    @When("^I press an arrow key towards that cell$")
    public void i_press_an_arrow_key_towards_that_cell() {
        getGame().move(player, whereToGo);
    }

    /**
     * Check to see if the player is still on the same square.
     */
    @Then("^the move is not conducted$")
    public void the_move_is_not_conducted() {
        assertThat(player.getSquare()).isEqualTo(square);
    }

    /**
     * Make sure the player is set to move to the south,
     * and check whether the south contains a ghost.
     */
    @Given("^my Pacman is next to a cell containing a ghost$")
    public void my_pacman_next_to_a_cell_containing_a_ghost() {
        // Get the player's square
        square = player.getSquare();
        // Get a square which contains a pellet according to  board.txt
        newSquare = square.getSquareAt(Direction.SOUTH);
        // Define the direction we want to move to
        whereToGo = Direction.SOUTH;
        // Make sure the square does indeed contain a pellet
        assertThat(newSquare.getOccupants().get(0) instanceof Ghost).isTrue();
    }

    /**
     * Check that the player is not alive.
     */
    @Then("^my Pacman dies$")
    public void my_pacman_dies() {
        assertThat(player.isAlive()).isFalse();
    }

    /**
     * Check if the game is no longer in progress.
     */
    @Then("^the game is over$")
    public void the_game_is_over() {
        assertThat(getGame().isInProgress()).isFalse();
    }

    /**
     * Move the player to the only pellet of the map,
     * and check whether there are no pellets left.
     */
    @When("^I have eaten the last pellet$")
    public void i_have_eaten_my_last_pellet() {
        getGame().move(player, Direction.EAST);
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
    }

    /**
     * Check whether the LevelObserver was notified once
     * about a won level.
     */
    @Then("^I win the game$")
    public void i_win_the_game() {
        assertThat(observer.getLevelsWon()).isEqualTo(1);
    }

    /**
     * Close the UI after all tests are finished.
     */
    @After("@move_the_player")
    public void tearDownUI() {
        launcher.dispose();
    }

}


