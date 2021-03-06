package nl.tudelft.jpacman.e2e.group81.suspend;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Entry point for running the the Cucumber tests in JUnit.
 *
 * @author Jan-Willem Gmelig Meyling
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        //plugin = {"pretty"},
        snippets = SnippetType.CAMELCASE,
        glue = {"nl.tudelft.jpacman.e2e.group81.suspend"},
        features = "classpath:group81features/suspend")
public class SuspendTest {

    /*
     * This class should be empty, step definitions should be in separate classes.
     */

}