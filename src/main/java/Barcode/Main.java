package Barcode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ProductManager prodManager = new ProductManager("data/products.csv");
        BarcodeFrame frame = new BarcodeFrame(prodManager);
        frame.updateInventoryPanel();

        // JSON Test
        ObjectMapper om = new ObjectMapper();
        JsonNode test = om.readTree(new File("data/test.json"));
        System.out.println(test.size());
    }
}
