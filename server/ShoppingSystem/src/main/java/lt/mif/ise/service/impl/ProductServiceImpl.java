package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		productRepository.deleteAll();
		if (productRepository.count() < 100) {
			List<Product> products = IntStream.range(0, 100)
				.mapToObj((i) -> {
					Product p = new Product();
					p.setProductId("prod-" + i);
					p.setDescription("" + p.hashCode());
					p.setName("Product NR" + i);
					p.setPrice(new BigDecimal("" + i + ".99"));
					return p;
				})
				.collect(Collectors.toList());
			productRepository.saveAll(products);
		}
	}

    @Override
    public Product getById(String id) {
        return productRepository.findByProductId(id).orElseThrow(()->new RuntimeException("Failed to get product."));
    }

    @Override
    public Page<Product> findProducts(ProductCriteria criteria, Pageable page) {
    	if (criteria.isUnspecified()) return productRepository.findAll(page);
        return productRepository.findAll(buildProductSpec(criteria), page);
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
