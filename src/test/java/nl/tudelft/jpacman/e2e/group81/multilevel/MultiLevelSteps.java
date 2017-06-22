package nl.tudelft.jpacman.e2e.group81.multilevel;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.group81.MultiLevelLauncher;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


/**
 *  @author Lars Ysla
 */
public class MultiLevelSteps {

    private MultiLevelLauncher launcher;
    private Game game;
    private Level.LevelObserver levelObserverMock;

    public Player getPlayer() {
        return game.getPlayers().get(0);
    }

    public Level getLevel() {
        return game.getLevel();
    }

    @Before("@multilevel")
    public void setup() {
        launcher = new MultiLevelLauncher();
        launcher.withMapFile("/testMap2.txt");
        launcher.launch();
        game = launcher.getGame();
        levelObserverMock = mock(Level.LevelObserver.class);
        game.getLevel().addObserver(levelObserverMock);
    }

    @Given("^the game has started$")
    public void theGameHasStarted() {
        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * @throws PendingException
     */
    @Given("^the current level is less than four$")
    public void theCurrentLevelIsLessThanFour() {
        verify(levelObserverMock, atMost(3)).levelWon();
    }

    /**
     * @throws PendingException
     */
    @Given("^my Pacman is next to a square containing a pellet$")
    public void myPacmanIsNextToASquareContainingAPellet() throws Throwable {
        Square eastSquare = getPlayer().getSquare().getSquareAt(Direction.EAST);
        assertThat(eastSquare.getOccupants().get(0) instanceof Pellet).isTrue();
    }

    /**
     * @throws PendingException
     */
    @When("^I have eaten the last pellet$")
    public void iHaveEatenTheLastPellet() {
        game.move(getPlayer(), Direction.EAST);
    }

    /**
     * @throws PendingException
     */
    @Then("^I win the level$")
    public void iWinTheLevel() {
        verify(levelObserverMock, times(1)).levelWon();
    }

    /**
     * @throws PendingException
     */
    @Then("^the next level starts$")
    public void theNextLevelStarts() {
        assertThat(game.isInProgress()).isTrue();
        assertThat(game.getLevel().remainingPellets() > 0).isTrue();
    }

    /**
     * @throws PendingException
     */
    @Given("^the current level is four$")
    public void theCurrentLevelIsFour() {
        for (int levelNr = 0; levelNr < 3; levelNr++) {
            game.getLevel().move(getPlayer(), Direction.EAST);
            game.getLevel().addObserver(levelObserverMock);
        }
        verify(levelObserverMock, times(3)).levelWon();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * @throws PendingException
     */
    @Then("^I win the game$")
    public void iWinTheGame() {
        verify(levelObserverMock, times(4)).levelWon();
        assertThat(game.isInProgress()).isFalse();
    }

    @After("@multilevel")
    public void tearDownUI() {
        launcher.dispose();
    }
}
