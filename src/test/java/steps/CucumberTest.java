package steps;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Entry point for running the the Cucumber tests in JUnit.
 *
 * This is adapted from e2e.framework.startup.StartupTest
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        snippets = SnippetType.CAMELCASE,
        glue = {"cucumber.steps"},
        features = "classpath:frameworkfeatures")
public class CucumberTest {

    /*
     * This class should be empty, step definitions should be in separate classes.
     */

}