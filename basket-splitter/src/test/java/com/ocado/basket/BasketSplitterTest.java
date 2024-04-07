package com.ocado.basket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for Basket Splitter.
 */
public class BasketSplitterTest {

    private String configTestFilePath;
    private String CONFIG_TEST_FILE_1 = "configTest1.json";
    private String CONFIG_TEST_FILE_2 = "configTest2.json";
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

    /**
     * Small split test
     * @throws IOException 
     */
    @Test
    public void smallSplitTest() throws IOException {
        String configFilePath = configTestFilePath + "\\" + CONFIG_TEST_FILE_2;
        BasketSplitter basketSplitter = new BasketSplitter(configFilePath);
        List<String> items = List.of("Steak (300g)",
                                        "Carrots (1kg)",
                                        "AA Battery (4 Pcs.)",
                                        "Espresso Machine",
                                        "Garden Chair",
                                        "Cold Beer (330ml)");

        Map<String, List<String>> splited = basketSplitter.split(items);

        List<String> edList = splited.get("Express Delivery");
        Collections.sort(edList);
        String res1 = edList.toString();
        assertTrue(res1.equals("[AA Battery (4 Pcs.), Carrots (1kg), Cold Beer (330ml), Steak (300g)]"));
    }

    /**
     * Empty list split test
     * @throws IOException 
     */
    @Test
    public void emptyListSplitTest() throws IOException {
        String configFilePath = configTestFilePath + "\\" + CONFIG_TEST_FILE_2;
        BasketSplitter basketSplitter = new BasketSplitter(configFilePath);
        List<String> items = List.of();

        Map<String, List<String>> splited = basketSplitter.split(items);

        assertTrue(splited.toString().equals("{}"));
    }
}
