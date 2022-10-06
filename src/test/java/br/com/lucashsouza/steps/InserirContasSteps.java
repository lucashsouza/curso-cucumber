package br.com.lucashsouza.steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

public class InserirContasSteps {

    private WebDriver driver;

    @Dado("que estou acessando a aplicação")
    public void queEstouAcessandoAAplicação() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.get("https://seubarriga.wcaquino.me/");
    }

    @Quando("informo o usuário {string}")
    public void informoOUsuário(String string) {
        driver.findElement(By.id("email")).sendKeys(string);
    }

    @Quando("a senha {string}")
    public void aSenha(String string) {
        driver.findElement(By.id("senha")).sendKeys(string);
    }

    @Quando("seleciono entrar")
    public void selecionoEntrar() {
        driver.findElement(By.tagName("button")).click();
    }

    @Então("visualizo a página inicial")
    public void visualizoAPáginaInicial() {
        String texto = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Bem vindo, Lucas!", texto);
    }

    @Quando("seleciono Contas")
    public void selecionoContas() {
        driver.findElement(By.linkText("Contas")).click();
    }

    @Quando("seleciono Adicionar")
    public void selecionoAdicionar() {
        driver.findElement(By.linkText("Adicionar")).click();
    }

    @Quando("informo a conta {string}")
    public void informoAConta(String string) {
        driver.findElement(By.id("nome")).sendKeys(string);
    }

    @Quando("seleciono Salvar")
    public void selecionoSalvar() {
        driver.findElement(By.tagName("button")).click();
    }

    @Então("recebo a mensagem {string}")
    public void receboAMensagem(String string) {
        String texto = driver.findElement(By.xpath("//div[starts-with(@class, 'alert alert-')]")).getText();
        Assert.assertEquals(string, texto);
    }

    @After(order = 1, value = "not @unitários")
    public void screenshot(Scenario cenario) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("target/screenshot/"
                    + cenario.getName() + "/" + cenario.getId() + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After(order = 0, value = "not @unitários")
    public void fecharNavegador() {
        driver.quit();
    }
}
