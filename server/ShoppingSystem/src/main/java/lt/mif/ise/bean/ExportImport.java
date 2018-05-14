package lt.mif.ise.bean;

import lt.mif.ise.domain.Product;

public interface ExportImport {
    Iterable<Product> importProducts(String path);

    String exportProducts();
}
