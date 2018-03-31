package lt.mif.ise.service;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;

import java.util.List;

public interface ProductService {
    Product getById(String id);
    
    Iterable<Product> findProducts(ProductCriteria search);

    Product create(Product product);

    Product modify(Product product);

    void delete(String productId);
}
