package Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		features = {"src/test/resources/featurefiles" }, 
		glue = { "step_definitions" }, 
		plugin = {"pretty","html:target/cucumber-html-reports","json:target/cucumber.json", "junit:target/cucumber-results.xml"}, 
		tags = {"@TodoSanityTest"}, 
		monochrome=true, snippets = SnippetType.CAMELCASE)
public class RunTest extends AbstractTestNGCucumberTests{

}