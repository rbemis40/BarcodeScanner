package Barcode;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ProductManager prodManager = new ProductManager("data/products.csv");
        BarcodeFrame frame = new BarcodeFrame(prodManager);
        frame.updateInventoryPanel();
    }
}
