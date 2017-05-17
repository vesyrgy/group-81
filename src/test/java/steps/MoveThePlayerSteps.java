package steps;

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
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.SpriteStore;

/**
 * @author Lars Ysla
 */
public class MoveThePlayerSteps {

    private Launcher launcher;
    private Game getGame() { return launcher.getGame(); }
    private Player player;
    private Square square;
    private Square newSquare;
    private Direction whereToGo;
    private int score;


    @Before("@framework")
    public void setup() {
        //  use a map where the Pacman is next to a pellet, a wall, and an empty cell
        launcher = new MyExtension().withMapFile("/testMap1.txt");
        launcher.launch();
    }


    @Given("^the game has started$")
    public void the_game_has_started() {
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();

        //  Set the player and score variables
        player = getGame().getPlayers().get(0);
        score = player.getScore();
    }

    @Given("^my Pacman is next to a square containing a pellet$")
    public void my_Pacman_is_next_to_a_square_containing_a_pellet() throws Throwable {
        // Get the player's square
        square = player.getSquare();
        //  Pick the direrection of an empty square in textMap1.txt & set the destination square
        whereToGo = Direction.EAST;
        newSquare = square.getSquareAt(whereToGo);
        // Make sure the square does indeed contain a pellet
        assertThat(newSquare.getOccupants().get(0) instanceof Pellet).isTrue();

    }

    @When("^I press an arrow key towards that square$")
    public void i_press_an_arrow_key_towards_that_square() throws Throwable {
        // simulate a call to move(), using whereToGo, which is a direction defined by an @Given method
        getGame().move(player,whereToGo);

    }

    @Then("^my Pacman can move to that square$")
    public void my_Pacman_can_move_to_that_square() throws Throwable {
        //  check to see that the current square is the square to which we wanted to move
        assertThat(player.getSquare().equals(newSquare)).isTrue();
    }

    @Then("^I earn the points for the pellet$")
    public void i_earn_the_points_for_the_pellet() throws Throwable {
        //  check if the score has been incremented
        assertThat(player.getScore() > score).isTrue();
    }

    @Then("^the pellet disappears from that square$")
    public void the_pellet_disappears_from_that_square() throws Throwable {
        //  check to see if the square to which the player has moved no longer contains a Pellet
        for(Unit occupant: player.getSquare().getOccupants()) {
            assertThat(occupant instanceof Pellet).isFalse();
        }
    }

    @Given("^my Pacman is next to an empty square$")
    public void my_Pacman_is_next_to_an_empty_square() throws Throwable {
        square = player.getSquare();
        //  Pick the direrection of an empty square in textMap1.txt & set the destination square
        whereToGo = Direction.WEST;
        newSquare = square.getSquareAt(whereToGo);
        //  Check that the square is empty
        assertThat(newSquare.getOccupants().isEmpty()).isTrue();
    }

    @Then("^my points remain the same$")
    public void my_points_remain_the_same() throws Throwable {
        //  check if the score is still the same
        assertThat(player.getScore()).isEqualTo(score);
    }

    @Given("^my Pacman is next to a cell containing a wall$")
    public void my_Pacman_is_next_to_a_cell_containing_a_wall() throws Throwable {
        square = player.getSquare();
        //  Pick the direrection of a wall in textMap1.txt & set the destination square
        whereToGo = Direction.NORTH;
        newSquare = square.getSquareAt(whereToGo);
        //  Check if the cell is a wall (assuming walls are the only inaccessible squares)
        assertThat(newSquare.isAccessibleTo(player)).isFalse();
    }

    @When("^I press an arrow key towards that cell$")
    public void i_press_an_arrow_key_towards_that_cell() throws Throwable {
        // simulate a call to move(), using whereToGo, which is a direction defined by an @Given method
        getGame().move(player,whereToGo);
    }

    @Then("^the move is not conducted$")
    public void the_move_is_not_conducted() throws Throwable {
        //  Check to see if the player is still on the same square
        assertThat(player.getSquare()).isEqualTo(square);
    }

    @When("^the user presses the \"([^\"]*)\" button$")
    public void the_user_presses_the_button(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^then all moves from ghosts and the player are suspended$")
    public void then_all_moves_from_ghosts_and_the_player_are_suspended() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @After("@framework")
    public void tearDownUI() {
        launcher.dispose();
    }

}


