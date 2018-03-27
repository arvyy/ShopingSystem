package lt.mif.ise.service;

import lt.mif.ise.domain.Product;

import java.util.List;

public interface ProductService {
    Product getById(String id);

    List<Product> getByName(String name);

    Product create(Product product);

    Product modify(Product product);

    void delete(String productId);
}
