package steps;

import static org.assertj.core.api.Assertions.assertThat;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.game.*;
import nl.tudelft.jpacman.group81.MyExtension;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.board.*;

/**
 * @author Lars Ysla
 */
public class MoveThePlayerSteps {

    private MyExtension launcher;
    private Game getGame() { return launcher.getGame(); }
    private Player player;
    private Square square;
    private Square newSquare;
    private Direction whereToGo;


    @Before("@framework")
    public void setup() {
        launcher = new MyExtension();
        launcher.launch();
    }


    @Given("^the game has started$")
    public void the_game_has_started() {
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    @Given("^my Pacman is next to a square containing a pellet$")
    public void my_Pacman_is_next_to_a_square_containing_a_pellet() throws Throwable {
        // Get the player and the square
        player = getGame().getPlayers().get(0);
        square = player.getSquare();
        // Get a square which contains a pellet according to  board.txt
        newSquare = square.getSquareAt(Direction.EAST);
        // Define the direction we want to move to
        whereToGo = Direction.EAST;
        // Make sure the square does indeed contain a pellet
        assertThat(square.getOccupants().contains(Pellet.class)).isTrue();

    }

    @When("^I press an arrow key towards that square$")
    public void i_press_an_arrow_key_towards_that_square() throws Throwable {
        // simulate a call to move(), using whereToGo, which is a direction defined by an @Given method
        getGame().move(player, whereToGo);

    }

    @Then("^my Pacman can move to that square$")
    public void my_Pacman_can_move_to_that_square() throws Throwable {
        //  check that the square to which we want to move is accessible
        assertThat(player.getSquare().getSquareAt(whereToGo).isAccessibleTo(player)).isTrue();
        //  check to see that the current square is the square to which we wanted to move
        assertThat(player.getSquare().equals(newSquare));

    }

    @Then("^I earn the points for the pellet$")
    public void i_earn_the_points_for_the_pellet() throws Throwable {

    }

    @Then("^the pellet disappears from that square$")
    public void the_pellet_disappears_from_that_square() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Given("^my Pacman is next to an empty square$")
    public void my_Pacman_is_next_to_an_empty_square() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Then("^my points remain the same$")
    public void my_points_remain_the_same() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Given("^my Pacman is next to a cell containing a wall$")
    public void my_Pacman_is_next_to_a_cell_containing_a_wall() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @When("^I press an arrow key towards that cell$")
    public void i_press_an_arrow_key_towards_that_cell() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Then("^the move is not conducted$")
    public void the_move_is_not_conducted() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @When("^the user presses the \"([^\"]*)\" button$")
    public void the_user_presses_the_button(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Then("^then all moves from ghosts and the player are suspended$")
    public void then_all_moves_from_ghosts_and_the_player_are_suspended() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @After("@framework")
    public void tearDownUI() {
        launcher.dispose();
    }

}

