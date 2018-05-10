package lt.mif.ise.service;

import lt.mif.ise.domain.Category;

import java.util.List;

public interface CategoryService {
    Category getById(Long id);

    Category getByName(String name);

    Iterable<Category> getAll();

    Category create(Category category);

    Category modify(Category category);

    void delete(Long id);
}
