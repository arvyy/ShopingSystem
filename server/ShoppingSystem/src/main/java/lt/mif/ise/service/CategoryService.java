package lt.mif.ise.service;

import lt.mif.ise.domain.Category;

public interface CategoryService {

    Iterable<Category> getAll();

    Category getOrCreate(String catName);
    
    void deleteIfUnused(String catName);
    
}
