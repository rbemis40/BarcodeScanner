package Barcode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProductReader {
    private final FileReader fr;
    private final BufferedReader br;
    private String filename;

    public ProductReader(String prodFile) throws FileNotFoundException {
        filename = prodFile;

        fr = new FileReader(prodFile);
        br = new BufferedReader(fr);
    }

    public ArrayList<Product> readProducts() throws IOException {
        ArrayList<Product> fileProducts = new ArrayList<>();

        String curLine = br.readLine();
        if (!checkFirstLine(curLine)) {
            throw new IOException("Invalid Product File");
        }

        while((curLine = br.readLine()) != null) {
            Product curProd = parseLine(curLine);
            fileProducts.add(curProd);
        }

        return fileProducts;
    }

    public void close() {
        try {
            br.close();
            fr.close();
        } catch (IOException e) { return; }
    }

    // Checks that the first line has the correct column names
    private boolean checkFirstLine(String firstLine) {
        String[] colNames = firstLine.split(",");
        return colNames.length == 3 && colNames[0].equals("UPC") && colNames[1].equals("SKU") && colNames[2].equals("Quantity");
    }

    private Product parseLine(String line) throws IOException {
        String[] values = line.split(",");
        if (values.length != 3) {
            throw new IOException("Invalid product information");
        }

        // First value is the UPC, second is the SKU, third is the quantity
        long upc;
        try {
            upc = Long.parseLong(values[0]);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid UPC");
        }

        String sku = values[1];
        int quantity;

        try {
            quantity = Integer.parseInt(values[2]);
        } catch(NumberFormatException e) {
            throw new IOException("Invalid Quantity");
        }

        return new Product(upc, sku, quantity);
    }
}
