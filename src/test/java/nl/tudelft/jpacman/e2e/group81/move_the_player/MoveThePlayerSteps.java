package nl.tudelft.jpacman.e2e.group81.move_the_player;

import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.*;
import nl.tudelft.jpacman.group81.MyExtension;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.board.*;
import nl.tudelft.jpacman.npc.ghost.Ghost;

/**
 * @author Lars Ysla
 */
public class MoveThePlayerSteps {

    private static class TestObserver implements Level.LevelObserver {
        private int levelsWon = 0;
        private int levelsLost = 0;

        public int getLevelsWon() {
            return levelsWon;
        }

        public int getLevelsLost() {
            return levelsLost;
        }

        public void levelWon() {
            levelsWon ++;
        }

        public void levelLost() {
            levelsLost ++;
        }
    }

    private Launcher launcher;
    private Game getGame() { return launcher.getGame(); }
    private Player player;
    private Square square;
    private Square newSquare;
    private Direction whereToGo;
    private int score;
    private TestObserver observer;


    @Before("@move_the_player")
    public void setup() {
        //  use a map where the Pacman is next to a pellet, a wall, and an empty cell
        launcher = new MyExtension().withMapFile("/testMap1.txt");
        launcher.launch();
    }


    @Given("^the game has started$")
    public void the_game_has_started() {
        observer = new TestObserver();
        getGame().getLevel().addObserver(observer);
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();

        //  Set the player and score variables
        player = getGame().getPlayers().get(0);
        score = player.getScore();
    }

    @Given("^my Pacman is next to a square containing a pellet$")
    public void my_Pacman_is_next_to_a_square_containing_a_pellet() {
        // Get the player's square
        square = player.getSquare();
        //  Pick the direrection of an empty square in textMap1.txt & set the destination square
        whereToGo = Direction.EAST;
        newSquare = square.getSquareAt(whereToGo);
        // Make sure the square does indeed contain a pellet
        assertThat(newSquare.getOccupants().get(0) instanceof Pellet).isTrue();

    }

    @When("^I press an arrow key towards that square$")
    public void i_press_an_arrow_key_towards_that_square() {
        // simulate a call to move(), using whereToGo, which is a direction defined by an @Given method
        getGame().move(player,whereToGo);

    }

    @Then("^my Pacman can move to that square$")
    public void my_Pacman_can_move_to_that_square() {
        //  check to see that the current square is the square to which we wanted to move
        assertThat(player.getSquare()).isEqualTo(newSquare);
    }

    @Then("^I earn the points for the pellet$")
    public void i_earn_the_points_for_the_pellet() {
        //  check if the score has been incremented
        assertThat(player.getScore() > score).isTrue();
    }

    @Then("^the pellet disappears from that square$")
    public void the_pellet_disappears_from_that_square() {
        //  check to see if the square to which the player has moved no longer contains a Pellet
        for(Unit occupant: player.getSquare().getOccupants()) {
            assertThat(occupant instanceof Pellet).isFalse();
        }
    }

    @Given("^my Pacman is next to an empty square$")
    public void my_Pacman_is_next_to_an_empty_square() {
        square = player.getSquare();
        //  Pick the direrection of an empty square in textMap1.txt & set the destination square
        whereToGo = Direction.WEST;
        newSquare = square.getSquareAt(whereToGo);
        //  Check that the square is empty
        assertThat(newSquare.getOccupants().isEmpty()).isTrue();
    }

    @Then("^my points remain the same$")
    public void my_points_remain_the_same() {
        //  check if the score is still the same
        assertThat(player.getScore()).isEqualTo(score);
    }

    @Given("^my Pacman is next to a cell containing a wall$")
    public void my_Pacman_is_next_to_a_cell_containing_a_wall() {
        square = player.getSquare();
        //  Pick the direrection of a wall in textMap1.txt & set the destination square
        whereToGo = Direction.NORTH;
        newSquare = square.getSquareAt(whereToGo);
        //  Check if the cell is a wall (assuming walls are the only inaccessible squares)
        assertThat(newSquare.isAccessibleTo(player)).isFalse();
    }

    @When("^I press an arrow key towards that cell$")
    public void i_press_an_arrow_key_towards_that_cell() {
        // simulate a call to move(), using whereToGo, which is a direction defined by an @Given method
        getGame().move(player,whereToGo);
    }

    @Then("^the move is not conducted$")
    public void the_move_is_not_conducted() {
        //  Check to see if the player is still on the same square
        assertThat(player.getSquare()).isEqualTo(square);
    }

    @When("^the user presses the \"([^\"]*)\" button$")
    public void the_user_presses_the_button(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^then all moves from ghosts and the player are suspended$")
    public void then_all_moves_from_ghosts_and_the_player_are_suspended() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

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

    @Then("^my Pacman dies$")
    public void my_pacman_dies() {
        assertThat(player.isAlive()).isFalse();
    }

    @Then("^the game is over$")
    public void the_game_is_over() {
        assertThat(getGame().isInProgress()).isFalse();
    }

    @When("^I have eaten the last pellet$")
    public void i_have_eaten_my_last_pellet() {
        getGame().move(player, Direction.EAST);
        assertThat(getGame().getLevel().remainingPellets()).isEqualTo(0);
    }

    @Then("^I win the game$")
    public void i_win_the_game() {
        assertThat(observer.getLevelsWon()).isEqualTo(1);
    }

    @After("@move_the_player")
    public void tearDownUI() {
        launcher.dispose();
    }

}


