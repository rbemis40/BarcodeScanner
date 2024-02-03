package Barcode;

import javax.swing.*;
import java.io.IOException;

// Creates and manages product lists, readers, and writers and facilitates communication between them
public class ProductManager {
    private ProductList fileProducts; // Products found on file
    private ProductList scannedProducts; // Products scanned from BarcodeFrame
    public ProductManager(String prodFile) throws IOException {
        scannedProducts = new ProductList();
        updateFileProducts(prodFile);
    }

    // Adds the new product to the scannedProducts list, and updates the product WITH the added SKU
    public void addScannedProduct(Product newProd) {
        scannedProducts.add(newProd);
    }

    // This adds a new product with quantity 0 to file products, to use as a reference in case that UPC is seen again
    public void addReferenceProduct(Product prod) {
        Product refProd = new Product(prod.upc, prod.sku, 0);
        fileProducts.add(refProd);
    }

    // Attempts to search the file products for the SKU if possible
    public void searchFileForSku(Product prod) throws ProductList.ProductNotFoundException{
        prod.sku = fileProducts.upcToSku(prod.upc);
    }

    public ProductList getFileProducts() {
        return new ProductList(fileProducts);
    }

    private void updateFileProducts(String prodFile) throws IOException {
        ProductReader reader = new ProductReader(prodFile);
        fileProducts = reader.readProducts();
    }
}
