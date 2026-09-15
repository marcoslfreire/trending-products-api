package com.praticando.backend.product;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extensão JUnit 5 que loga, de forma legível, o início/fim de cada teste.
 * Não precisa chamar nada manualmente dentro dos métodos de teste — o JUnit
 * invoca os callbacks (testSuccessful, testFailed) sozinho no ciclo de vida.
 */
public class ReadableTestWatcher implements TestWatcher {

    private static final Logger LOG = LoggerFactory.getLogger("TESTES");

    @Override
    public void testSuccessful(ExtensionContext context) {
        LOG.info("✅ PASSOU  — {}", nomeLegivel(context));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LOG.error("❌ FALHOU  — {} | motivo: {}", nomeLegivel(context), cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        LOG.warn("⏭️  PULOU   — {}", nomeLegivel(context));
    }

    private String nomeLegivel(ExtensionContext context) {
        return context.getDisplayName();
    }
}