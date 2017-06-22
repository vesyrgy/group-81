package nl.tudelft.jpacman.e2e.group81.multilevel;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.group81.MultiLevelLauncher;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


/**
 * Cucumber steps for multi level scenarios.
 *  @author Lars Ysla
 */
public class MultiLevelSteps {

    private MultiLevelLauncher launcher;
    private Game game;
    private Level.LevelObserver levelObserverMock;

    private Player getPlayer() {
        return game.getPlayers().get(0);
    }

    /**
     * Getter for level.
     * @return the Level.
     */
    public Level getLevel() {
        return game.getLevel();
    }

    /**
     * Launches the GUI with a testing map
     * and prepares for steps.
     */
    @Before("@multilevel")
    public void setup() {
        launcher = new MultiLevelLauncher();
        launcher.withMapFile("/testMap2.txt");
        launcher.launch();
        game = launcher.getGame();
        levelObserverMock = mock(Level.LevelObserver.class);
        game.getLevel().addObserver(levelObserverMock);
    }

    /**
     * Starts a multi-level game.
     */
    @Given("^the game has started$")
    public void theGameHasStarted() {
        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Checks that the level is less than four.
     */
    @Given("^the current level is less than four$")
    //CHECK:OFF: MagicNumber
    public void theCurrentLevelIsLessThanFour() {
        verify(levelObserverMock, atMost(3)).levelWon();
    }
    //CHECK:ON: MagicNumber

    /**
     * Checks that in the loaded test map the player is next to a pellet.
     */
    @Given("^my Pacman is next to a square containing a pellet$")
    public void myPacmanIsNextToASquareContainingAPellet() {
        Square eastSquare = getPlayer().getSquare().getSquareAt(Direction.EAST);
        assertThat(eastSquare.getOccupants().get(0) instanceof Pellet).isTrue();
    }

    /**
     * Move player to the singular pellet to eat all pellets.
     */
    @When("^I have eaten the last pellet$")
    public void iHaveEatenTheLastPellet() {
        game.move(getPlayer(), Direction.EAST);
    }

    /**
     * Check whether the level was won.
     */
    @Then("^I win the level$")
    public void iWinTheLevel() {
        verify(levelObserverMock, times(1)).levelWon();
    }

    /**
     * Check whether a new level has started.
     */
    @Then("^the next level starts$")
    public void theNextLevelStarts() {
        assertThat(game.isInProgress()).isTrue();
        assertThat(game.getLevel().remainingPellets() > 0).isTrue();
    }

    /**
     * Win three levels to reach the fourth level.
     */
    @Given("^the current level is four$")
    public void theCurrentLevelIsFour() {
        //CHECK:OFF: MagicNumber
        for (int levelNr = 0; levelNr < 3; levelNr++) {
            game.getLevel().move(getPlayer(), Direction.EAST);
            game.getLevel().addObserver(levelObserverMock);
        }
        verify(levelObserverMock, times(3)).levelWon();
        //CHECK:ON: MagicNumber
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Check that the whole game is won.
     */
    @Then("^I win the game$")
    public void iWinTheGame() {
        //CHECK:OFF: MagicNumber
        verify(levelObserverMock, times(4)).levelWon();
        //CHECK:ON: MagicNumber
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Close the GUI.
     */
    @After("@multilevel")
    public void tearDownUI() {
        launcher.dispose();
    }
}
