package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Iterator;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

public class BasketSplitter {
    
    public Map<String, List<String>> config;

    public BasketSplitter(String absolutePathToConfigFile) throws IOException {
        Gson gson = new Gson();
        FileReader configFileReader = new FileReader(absolutePathToConfigFile);
        TypeToken<Map<String, List<String>>> configMapTypeToken = new TypeToken<Map<String, List<String>>>() {};

        config = gson.fromJson(configFileReader, configMapTypeToken.getType());
    }

    public Map<String, List<String>> split(List<String> items) {
        // Map to count how many products can be delivery by delivery type
        Map<String, Integer> howCommonDeliveryTypes = new HashMap<String, Integer>();

        for (String item : items) {
            List<String> delivery_types = config.get(item);
            for (String delivery_type : delivery_types) {
                if (howCommonDeliveryTypes.containsKey(delivery_type)) {
                    howCommonDeliveryTypes.put(delivery_type, howCommonDeliveryTypes.get(delivery_type) + 1);
                } else {
                    howCommonDeliveryTypes.put(delivery_type, 1);
                }
            }
        }

        System.out.println(howCommonDeliveryTypes);

        // Find the best delivery types
        LinkedHashMap<String, Integer> bestDeliveryTypes = howCommonDeliveryTypes.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        System.out.println(bestDeliveryTypes);

        // Map to count how many delivery types products have
        Map<String, Integer> howManyDeliveryTypes = new HashMap<String, Integer>();

        for (String item : items) {
            howManyDeliveryTypes.put(item, config.get(item).size());
        }

        // Find the hardest products
        LinkedHashMap<String, Integer> worstProducts = howManyDeliveryTypes.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        
        List<String> itemsLeft = items.stream().collect(Collectors.toList());
        Map<String, List<String>> result = new HashMap<String, List<String>>();

        // Iterate through worst products until we have products left
        Iterator<String> worstProductsIterator = worstProducts.keySet().iterator();
        while (worstProductsIterator.hasNext() && (!itemsLeft.isEmpty())) {
            String product = worstProductsIterator.next();

            String productBestDeliveryType = bestDeliveryTypes.entrySet()
                .stream()
                .filter(e -> config.get(product).contains(e.getKey()))
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

            if (!result.containsKey(productBestDeliveryType))
                result.put(productBestDeliveryType, new ArrayList<String>());

            Iterator<String> itemsLeftIterator = itemsLeft.iterator();
            while (itemsLeftIterator.hasNext()) {
                String item = itemsLeftIterator.next();
                if (config.get(item).contains(productBestDeliveryType)) {
                    System.out.println("New Product added: " + item + " to " + productBestDeliveryType);
                    result.get(productBestDeliveryType).add(item);
                    itemsLeftIterator.remove();
                }
            }
        }

        return result;
    }
}
