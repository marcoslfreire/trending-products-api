package org.example.pages;

import org.example.config.AutomationConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private static final String LOGIN_URL = AutomationConfig.baseUrl() + "/login.html";
    private final WebDriver driver;

    private WebElement campoEmail;
    private WebElement campoSenha;
    private WebElement botaoEntrar;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(LOGIN_URL);
        log.info("Página de login aberta.");
    }

    public boolean campoEmailEstaVisivel() {
        return driver.findElement(By.xpath("//*[@id=\"username\"]")).isDisplayed();
    }

    public void preencherEmail(String email) {
        campoEmail = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        campoEmail.sendKeys(email);
    }

    public void preencherSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha não foi informada.");
        }
        campoSenha = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        campoSenha.sendKeys(senha);
    }

    public void clicarEntrar() {
        botaoEntrar = driver.findElement(By.xpath("//*[@id=\"loginBtn\"]"));
        botaoEntrar.click();
    }

    public void realizarLogin(String email, String senha) throws InterruptedException {
        String tituloPage = String.valueOf(driver.findElement(By.xpath("/html/body/header/strong")));
        preencherEmail(email);
        preencherSenha(senha);
        clicarEntrar();

    }
}