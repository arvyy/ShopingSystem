package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.domain.search.ProductSearch;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/product")
@RestController
public class ProductRestController {
	/*
	 * TODO error handling mapping
	 */
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "id/{productId}")
    public Product getProduct(@PathVariable(value = "productId")String productId){
        return productService.getById(productId);
    }

    // Kaip programa zino, koki status code grazina?
    @RequestMapping(method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product){
        return productService.create(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Product modifyProduct(@RequestBody Product product){
        return productService.modify(product);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "id/{productId}")
    public void deleteProduct(@PathVariable(value= "productId") String productId){
        productService.delete(productId);
    }

    @RequestMapping(value="page", method = RequestMethod.GET)
    public Page<ProductSearch> findProducts(ProductCriteria criteria, Pageable page){
        return productService.findProducts(criteria, page);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Iterable<ProductSearch> findProductsList(ProductCriteria criteria) {
    	return productService.findProductsList(criteria);
    }
}
