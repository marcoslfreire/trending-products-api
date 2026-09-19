package org.example.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Centraliza a criação do WebDriver. Alternar entre modo visível e headless
 * não exige mudar código nenhum — só a forma de invocar (property ou env var).
 */
public final class WebDriverFactory {

    private WebDriverFactory() {
        // classe utilitária: não deve ser instanciada
    }

    public static WebDriver create() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (isHeadless()) {
            options.addArguments("--headless=new", "--window-size=1400,900");
        }

        return new ChromeDriver(options);
    }

    private static boolean isHeadless() {
        String viaSystemProperty = System.getProperty("automation.headless");
        if (viaSystemProperty != null) {
            return Boolean.parseBoolean(viaSystemProperty);
        }
        String viaEnvVar = System.getenv("AUTOMATION_HEADLESS");
        return Boolean.parseBoolean(viaEnvVar); // null vira "false" automaticamente
    }
}