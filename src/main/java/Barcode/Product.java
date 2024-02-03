package Barcode;

public class Product {
    long upc;
    String sku;
    int quantity;

    public Product(long upc, String sku, int quantity) {
        this.upc = upc;
        this.sku = sku;
        this.quantity = quantity;
    }
    public Product(long upc) {
        this(upc, "", 1);
    }

    public Product(long upc, int quantity) {
        this(upc, "", quantity);
    }

    @Override
    public String toString() {
        return "Product: UPC: " + upc + " SKU: " + sku + " Quantity: " + quantity;
    }
}
