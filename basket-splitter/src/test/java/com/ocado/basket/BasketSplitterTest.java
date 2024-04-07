package com.ocado.basket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Unit tests for Basket Splitter.
 */
public class BasketSplitterTest {

    private String configTestFilePath;
    private String CONFIG_TEST_FILE_1 = "configTest1.json";
    private String NON_EXISTING_CONFIG_TEST_FILE = "notConfigTest.json";
    private String CONFIG_TEST_FILE_WRONG_FORMAT = "configTestWrongFormat.json";

    /**
     * Find config test file
     */
    public BasketSplitterTest() {
        Path resourceDirectory = Paths.get("src", "test", "java", "com", "ocado", "basket", "resources");
        configTestFilePath = resourceDirectory.toFile().getAbsolutePath();
    }

    /**
     * Small config file read test
     * @throws IOException 
     */
    @Test
    public void smallConfigFileReadTest() throws IOException {
        String configFilePath = configTestFilePath + "\\" + CONFIG_TEST_FILE_1;
        BasketSplitter basketSplitter = new BasketSplitter(configFilePath);

        List<String> firstProductConfig = basketSplitter.config.get("Cookies Oatmeal Raisin");
        assertTrue(firstProductConfig.get(0).equals("Pick-up point"));
    }

    /**
     * BasketSplitter test with config file which doesnt exists
     * @throws IOException 
     */
    @Test
    public void notExistingConfigFileReadTest() throws IOException {
        String configFilePath = configTestFilePath + "\\" + NON_EXISTING_CONFIG_TEST_FILE;

        assertThrows(FileNotFoundException.class, () -> new BasketSplitter(configFilePath));
    }

    /**
     * BasketSplitter test with config file with wrong format
     * @throws IOException 
     */
    @Test
    public void wrongFormatConfigFileReadTest() throws IOException {
        String configFilePath = configTestFilePath + "\\" + CONFIG_TEST_FILE_WRONG_FORMAT;

        assertThrows(com.google.gson.JsonSyntaxException.class, () -> new BasketSplitter(configFilePath));
    }
}
