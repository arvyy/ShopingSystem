package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class ProductServiceImpl implements ProductService {
	
    @Autowired
    private ProductRepository productRepository;
    
    
	@PostConstruct
	public void init() {
		/*
		 * Jei nieko ner, pakist kazkokiu placeholder duomenu
		 */
		System.out.println(productRepository.count());
		if (productRepository.count() == 0) {
			Product p1 = new Product();
			p1.setName("Foo");
			p1.setPrice(new BigDecimal("2.00"));
			Product p2 = new Product();
			p2.setName("Bar");
			p2.setPrice(new BigDecimal("10.05"));
			productRepository.saveAll(Arrays.asList(p1, p2));
		}
	}

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Failed to get product."));
    }

    @Override
    public Iterable<Product> findProducts(ProductCriteria criteria) {
    	if (criteria.isUnspecified()) return productRepository.findAll();
        return productRepository.findAll(buildProductSpec(criteria));
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product modify(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteById(productId);
    }
    
    private Specification<Product> buildProductSpec(ProductCriteria search) {
    	/*
    	 * inline interface'o Specification implementacija.
    	 * ateity papildysim paieskai pagal kaina, kategorija, ir t.t.
    	 * 
    	 */
    	return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
    		return builder.like(root.get("name"), search.getText().orElse(""));
    	};
    }
}
