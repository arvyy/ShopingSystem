package lt.mif.ise.service;

import lt.mif.ise.domain.Category;

import java.util.List;

public interface CategoryService {

    Iterable<Category> getAll();

    Category getOrCreate(String catName);
    
    void deleteIfUnused(String catName);
    
}
