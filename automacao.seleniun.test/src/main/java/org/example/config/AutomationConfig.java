package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centraliza configuração da automação. Prioridade de leitura:
 * 1) system property (-Dchave=valor) — ajuste pontual numa execução
 * 2) variável de ambiente — configuração do ambiente (ex.: Jenkins)
 * 3) arquivo automation.properties — configuração local de desenvolvimento
 * Nunca hardcoded no código, e o arquivo de credenciais reais fica fora do Git.
 */
public final class AutomationConfig {

    private static final Properties FILE_PROPERTIES = loadPropertiesFile();

    private AutomationConfig() {
        // classe utilitária: não deve ser instanciada
    }

    public static String baseUrl() {
        return firstNonBlank(
                System.getProperty("automation.baseUrl"),
                System.getenv("AUTOMATION_BASE_URL"),
                FILE_PROPERTIES.getProperty("automation.baseUrl"), "http://localhost:8081"
        );
    }

    public static String username() {
        return required("automation.username", "AUTOMATION_USERNAME");
    }

    public static String password() {
        return required("automation.password", "AUTOMATION_PASSWORD");
    }

    private static String required(String propertyKey, String envVar) {
        String value = firstNonBlank(
                System.getProperty(propertyKey),
                System.getenv(envVar),
                FILE_PROPERTIES.getProperty(propertyKey),
                null
        );
        if (value == null) {
            throw new IllegalStateException(
                    "Configuração ausente: defina " + envVar + " (variável de ambiente), " +
                            "-D" + propertyKey + " (system property), ou a chave '" + propertyKey +
                            "' em automation/src/main/resources/automation.properties."
            );
        }
        return value;
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) return v;
        }
        return null;
    }

    private static Properties loadPropertiesFile() {
        Properties props = new Properties();
        try (InputStream in = AutomationConfig.class.getClassLoader()
                .getResourceAsStream("automation.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar automation.properties", e);
        }
        return props;
    }
}