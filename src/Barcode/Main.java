package Barcode;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ProductReader reader = new ProductReader("data/products.csv");
        ArrayList<Product> prods = reader.readProducts();
        reader.close();

        for (Product curProd : prods) {
            System.out.println(curProd);
        }

        BarcodeFrame frame = new BarcodeFrame();
    }
}
