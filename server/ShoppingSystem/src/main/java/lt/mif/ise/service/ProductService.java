package lt.mif.ise.service;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product getById(String id);
    
    Page<Product> findProducts(ProductCriteria search, Pageable page);

    Product create(Product product);

    Product modify(Product product);

    void delete(String productId);
}
