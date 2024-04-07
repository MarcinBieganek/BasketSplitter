package com.ocado.basket;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Basket Splitter.
 */
public class BasketSplitterTest {

    private String configTestFilePath;

    /**
     * Find config test file
     */
    @Before
    public void init() {
        Path resourceDirectory = Paths.get("src", "test", "java", "com", "ocado", "basket", "resources");
        configTestFilePath = resourceDirectory.toFile().getAbsolutePath();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }
}
