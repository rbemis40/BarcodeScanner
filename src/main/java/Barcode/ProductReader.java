package Barcode;

import java.io.*;
import java.util.ArrayList;

public class ProductReader {
    private final FileReader fr;
    private final BufferedReader br;
    private final String[] headerNames;

    public ProductReader(String prodFileName) throws IOException {
        headerNames = new String[]{"UPC", "SKU", "Quantity"};

        File prodFile = new File(prodFileName);
        boolean didCreate = prodFile.createNewFile(); // Ensure that the file exists
        if (didCreate) { // The file was just created, so needs to be populated with the correct header
            FileWriter fw = new FileWriter(prodFile);

            for (int i = 0; i < headerNames.length - 1; i++) {
                // TODO: Write the header to the file
                String writeStr = headerNames[i] + ",";
                fw.write(writeStr);
            }

            if (headerNames.length > 0) { // Write the last piece of the header without a comma if it exists
                fw.write(headerNames[headerNames.length - 1]);
            }

            fw.close();
        }

        fr = new FileReader(prodFile);
        br = new BufferedReader(fr);
    }

    public ProductList readProducts() throws IOException {
        ProductList fileProducts = new ProductList();

        String curLine = br.readLine();
        if (!checkHeader(curLine)) {
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
    private boolean checkHeader(String firstLine) {
        String[] colNames = firstLine.split(",");
        if (colNames.length != headerNames.length) {
            return false;
        }

        for (int i = 0; i < headerNames.length; i++) {
            if (!colNames[i].equals(headerNames[i])) {
                return false;
            }
        }

        return true;
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
