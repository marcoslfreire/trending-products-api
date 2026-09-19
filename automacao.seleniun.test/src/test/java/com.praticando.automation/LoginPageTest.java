package com.praticando.automation;

import org.example.config.AutomationConfig;
import org.example.config.WebDriverFactory;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.fail;

public class LoginPageTest {

    private static final Logger log =
            LoggerFactory.getLogger(LoginPageTest.class);

    @Test
    void publicarAnuncio() {

        WebDriver driver = WebDriverFactory.create();

        try {

            LoginPage loginPage = new LoginPage(driver);
            loginPage.open();

            if (loginPage.campoEmailEstaVisivel()) {

                log.info("Campo de e-mail encontrado.");

                loginPage.realizarLogin(AutomationConfig.username(), AutomationConfig.password());

                log.info("TESTE PASSOU!");

            } else {

                log.error("TESTE FALHOU!");
                log.error("Campo de e-mail não encontrado.");
                fail("Campo de e-mail não está visível na página de login.");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            driver.quit();
        }
    }
}
