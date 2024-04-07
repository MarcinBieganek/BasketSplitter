package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.IOException;

public class BasketSplitter {
    
    public Map<String, List<String>> config;

    public BasketSplitter(String absolutePathToConfigFile) throws IOException {
        Gson gson = new Gson();
        FileReader configFileReader = new FileReader(absolutePathToConfigFile);
        TypeToken<Map<String, List<String>>> configMapTypeToken = new TypeToken<Map<String, List<String>>>() {};

        config = gson.fromJson(configFileReader, configMapTypeToken);
    }
}
