package lt.mif.ise.bean;

import lt.mif.ise.domain.Product;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public interface ExportImport {
    Iterable<Product> importProducts(InputStream inputStream);

    ByteArrayOutputStream exportProducts();
}
