package br.com.lucashsouza.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "br.com.lucashsouza.steps",
        tags = "@unitários",
        plugin = {"pretty", "html:target/report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunnerTest {
}
