package nl.tudelft.jpacman.e2e.group81.move_the_player;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Entry point for running the the Cucumber tests in JUnit.
 *
 * This is adapted from e2e.group81.startup.StartupTest
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        snippets = SnippetType.CAMELCASE,
        glue = {"nl.tudelft.jpacman.e2e.group81.move_the_player"},
        features = "classpath:group81features/move_the_player")
public class MoveThePlayerTest {

    /*
     * This class should be empty, step definitions should be in separate classes.
     */

}