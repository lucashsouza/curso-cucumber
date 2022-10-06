package br.com.lucashsouza.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "br.com.lucashsouza.steps",
        tags = "@funcionais",
        plugin = {"pretty", "html:target/report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunnerFuncionalTest {

    @BeforeClass
    public static void reset() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://seubarriga.wcaquino.me/");
        driver.findElement(By.id("email")).sendKeys("lhenriquesouza00@gmail.com");
        driver.findElement(By.id("senha")).sendKeys("123456");
        driver.findElement(By.tagName("button")).click();
        driver.findElement(By.linkText("reset")).click();
        driver.quit();
    }
}
