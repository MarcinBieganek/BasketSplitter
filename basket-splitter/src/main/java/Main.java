import java.util.List;
import java.util.Map;

import com.ocado.basket.BasketSplitter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        try {
            
            BasketSplitter basket = new BasketSplitter("C:\\MARCIN\\INNE\\PROJEKT OCCADO\\BasketSplitter\\basket-splitter\\src\\test\\java\\com\\ocado\\basket\\resources\\configTest2.json");
            
            List<String> items = List.of("Steak (300g)",
                                        "Carrots (1kg)",
                                        "AA Battery (4 Pcs.)",
                                        "Espresso Machine",
                                        "Garden Chair",
                                        "Cold Beer (330ml)");

            Map<String, List<String>> res = basket.split(items);

            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}