package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.Category;
import lt.mif.ise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/category")
@RestController
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> getAllCategories(){
        return categoryService.getAll();
    }

}
