package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.Category;
import lt.mif.ise.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/category")
@RestController
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> getAllCategories(){
        return categoryService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{categoryId}")
    public Category getCategory(@PathVariable(value = "categoryId")Long categoryId){
        return categoryService.getById(categoryId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category createCategory(@RequestBody Category category){
        return categoryService.create(category);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Category modifyProduct(@RequestBody Category category){
        return categoryService.modify(category);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/name/{categoryName}")
    public Category getCategory(@PathVariable(value = "categoryName")String categoryName){
        return categoryService.getByName(categoryName);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/id/{categoryId}")
    public void deleteCategory(@PathVariable(value= "categoryId") Long categoryId){
        categoryService.delete(categoryId);
    }
}
