package nl.tudelft.jpacman.e2e.group81.suspend;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.tudelft.jpacman.Launcher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the Cucumber tests.
 */
public class SuspendSteps {

    private Launcher launcher;

    /**
     * Create a game and launch it.
     */
    @Before("@suspend")
    public void createGame() {
        launcher = new Launcher();
        launcher.launch();
    }

    /**
     * Start the game.
     */
    @Given("^the game has been started$")
    public void theGameHasStarted() {
        launcher.getGame().start();
    }

    /**
     * Stop the game.
     */
    @When("^the user presses the stop button$")
    public void theUserPressesTheStopButton() {
        launcher.getGame().stop();
    }

    /**
     * Verify that the game is not running.
     */
    @Then("^the game is suspended$")
    public void theGameIsSuspended() {
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

    /**
     * Start and stop a game.
     */
    @Given("^the game has been suspended$")
    public void theGameHasBeenSuspended() {
        launcher.getGame().start();
        launcher.getGame().stop();
    }

    /**
     * Start the game again.
     */
    @When("^the user presses the start button$")
    public void theUserPressesTheStartButton() {
        launcher.getGame().start();
    }

    /**
     * Verify that the game resumed.
     */
    @Then("^the game is resumed$")
    public void theGameIsResumed() {
        assertThat(launcher.getGame().isInProgress()).isTrue();
    }

    /**
     * Close the UI after all tests are finished.
     */
    @After("@suspend")
    public void tearDownUI() {
        launcher.dispose();
    }
}
