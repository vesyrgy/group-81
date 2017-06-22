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
    private Player player;
    private Level.LevelObserver levelObserverMock;

    @Before("@multilevel")
    public void setup() {
        launcher = new MultiLevelLauncher();
        launcher.withMapFile("testMap2.txt");
        launcher.launch();
        game = launcher.getGame();
        player = game.getPlayers().get(0);
        levelObserverMock = mock(Level.LevelObserver.class);
        game.getLevel().addObserver(levelObserverMock);
    }

    @Given("^the game has started$")
    public void theGameHasStarted() {
        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    @Given("^the current level is less than four$")
    public void theCurrentLevelIsLessThanFour() {
        verify(levelObserverMock, atMost(3)).levelWon();
    }

    @Given("^my Pacman is next to a square containing a pellet$")
    public void myPacmanIsNextToASquareContainingAPellet() throws Throwable {
        Square eastSquare = player.getSquare().getSquareAt(Direction.EAST);
        assertThat(eastSquare.getOccupants().get(0) instanceof Pellet).isTrue();
    }

    @When("^I have eaten the last pellet$")
    public void iHaveEatenTheLastPellet() {
        game.move(player, Direction.EAST);
        assertThat(game.getLevel().remainingPellets()).isEqualTo(0);
    }

    @Then("^I win the level$")
    public void iWinTheLevel() {
        verify(levelObserverMock, times(1)).levelWon();
    }

    @Then("^the next level starts$")
    public void theNextLevelStarts() {
        assertThat(game.isInProgress()).isTrue();
        verify(game.getLevel().remainingPellets() > 0);
    }

    @Given("^the current level is four$")
    public void theCurrentLevelIsFour() {
        for (int levelNr = 1; levelNr < 4; levelNr++) {
            game.move(player, Direction.EAST);
        }
        verify(levelObserverMock, times(3)).levelWon();
        assertThat(game.isInProgress()).isTrue();
    }

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
