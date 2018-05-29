package lt.mif.ise.service;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.domain.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product getById(String id);
    
    Page<ProductSearch> findProducts(ProductCriteria search, Pageable page);
    
	Iterable<ProductSearch> findProductsList(ProductCriteria criteria);

    Product save(Product product, boolean isNew);

    void delete(String productId);

    boolean isProductIdValid(String productId);

}
