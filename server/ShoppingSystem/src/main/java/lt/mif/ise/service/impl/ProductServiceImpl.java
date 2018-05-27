package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.domain.search.ProductSearch;
import lt.mif.ise.error.exception.NotFoundException;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.jpa.ProductSearchRepository;
import lt.mif.ise.service.CategoryService;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServiceImpl implements ProductService {
	
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Autowired
    private CategoryService categoryService;
    
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
					p.setPrice(new BigDecimal(1.99));
					p.setCategory(categoryService.getOrCreate("Category" + i%5));
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
    public Page<ProductSearch> findProducts(ProductCriteria criteria, Pageable page) {
    	if (criteria.isUnspecified()) return productSearchRepository.findAll(page);
        return productSearchRepository.findAll(buildProductSpec(criteria), page);
    }
    
    @Override
    public Iterable<ProductSearch> findProductsList(ProductCriteria criteria) {
    	if (criteria.isUnspecified()) return productSearchRepository.findAll();
        return productSearchRepository.findAll(buildProductSpec(criteria));
    }

    @Override
    public Product save(Product product, boolean isNew) {
    	if (!isNew) {
    		Product p = productRepository.findByProductId(product.getProductId()).orElseThrow(() -> new NotFoundException(String.format("Product with product id %s not found", product.getProductId())));
    		product.setId(p.getId());
            p.setProperties(product);
            return productRepository.save(p);
    	}
        return productRepository.save(product);
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteByProductId(productId);
    }
    
    private Specification<ProductSearch> buildProductSpec(ProductCriteria search) {
    	/*
    	 * inline interface'o Specification implementacija.
    	 * ateity papildysim paieskai pagal kaina, kategorija, ir t.t.
    	 * 
    	 */
    	return (Root<ProductSearch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
    		ArrayList<Predicate> predicates = new ArrayList<>();
    		search.getText().filter(t->!t.isEmpty()).ifPresent(t -> {
    			predicates.add(builder.like(root.get("name"), "%" + t + "%"));
    		});
    		search.getCategory().filter(t->!t.isEmpty()).ifPresent(t -> {
    			predicates.add(builder.equal(root.get("category").get("name"), t));
    		});
    		return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    	};
    }


}
