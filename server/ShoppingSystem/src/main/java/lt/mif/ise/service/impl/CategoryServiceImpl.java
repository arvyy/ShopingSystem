package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Category;
import lt.mif.ise.jpa.CategoryRepository;
import lt.mif.ise.jpa.ProductRepository;
import lt.mif.ise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Category getOrCreate(String catName) {
    	Category c = categoryRepository.findByName(catName);
    	if (c == null) {
    		c = new Category();
    		c.setName(catName);
    		c = categoryRepository.save(c);
    	}
    	return c;
    }
    
    @Override
    public void deleteIfUnused(String catName) {
    	Category c = categoryRepository.findByName(catName);
    	if (productRepository.countByCategory(c) == 0) {
    		categoryRepository.delete(c);
    	}
    }

    @Override
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }
}
