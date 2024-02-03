package Barcode;

import java.util.LinkedList;

// Used to convert between sku and upc
public class ProductList extends LinkedList<Product> {
    public ProductList() {
        super();
    }

    public ProductList(ProductList cpyList) {
        super(cpyList);
    }

    public String upcToSku(long upc) throws ProductNotFoundException {
        Product foundProd = findByUpc(upc);
        return foundProd.sku;
    }

    public long skuToUpc(String sku) throws ProductNotFoundException {
        Product foundProd = findBySku(sku);
        return foundProd.upc;
    }

    public Product findBySku(String sku) throws ProductNotFoundException {
        for (Product curProduct : this) {
            if (sku.equals(curProduct.sku)) {
                return curProduct;
            }
        }

        throw new ProductNotFoundException("SKU " + sku + " not found in product list");
    }

    public Product findByUpc(long upc) throws ProductNotFoundException {
        for (Product curProduct : this) {
            if (upc == curProduct.upc) {
                return curProduct;
            }
        }

        throw new ProductNotFoundException("UPC " + upc + " not found in product list");
    }

    public static class ProductNotFoundException extends Exception {
        public ProductNotFoundException() {
            super();
        }
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
}
