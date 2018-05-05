package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/product")
@RestController
public class ProductRestController {
	/*
	 * TODO error handling mapping
	 */
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "{productId}")
    public Product getProduct(@PathVariable(value = "productId")String productId){
        return productService.getById(productId);
    }

    // Kaip programa zino, koki status code grazina?
    @RequestMapping(method = RequestMethod.POST)
    public void createProduct(@RequestBody Product product){
        productService.create(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void modifyProduct(@RequestBody Product product){
        productService.modify(product);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}")
    public void deleteProduct(@PathVariable(value= "productId") String productId){
        productService.delete(productId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> findProducts(ProductCriteria criteria){
        return productService.findProducts(criteria);
    }
}
