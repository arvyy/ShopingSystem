package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Category;
import lt.mif.ise.jpa.CategoryRepository;
import lt.mif.ise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init(){
        //create some default categories
        if (categoryRepository.count() == 0){
            Category defaultCategory = new Category();
            defaultCategory.setName("Default category");
            categoryRepository.save(defaultCategory);
        }
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Failed to get category."));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category modify(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
