package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.domain.search.ProductSearch;
import lt.mif.ise.service.CategoryService;
import lt.mif.ise.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestMapping("/api/product")
@RestController
public class ProductRestController {
	/*
	 * TODO error handling mapping
	 */
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Value("${ise.productImage.location}")
    Resource productImgLocation;

    @RequestMapping(method = RequestMethod.GET, value = "id/{productId}")
    public Product getProduct(@PathVariable(value = "productId")String productId){
        return productService.getById(productId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data", "application/json"})
    public Product createProduct(
    		@RequestBody Product product,
    		@RequestParam(value = "isNew") boolean isNew, 
    		@RequestParam(value = "categoryName", required=false) String category,
    		@RequestParam(value = "file", required=false) MultipartFile file) throws IllegalStateException, IOException{
    	if (file != null && !file.isEmpty()) {
    		String fname = file.getOriginalFilename();
    		String type = fname.substring(fname.lastIndexOf("."));
    		String imgName = product.getProductId() + type;
    		product.setImageUrl("/product-image/" + imgName);
    		String path = new File(productImgLocation.getFile(), imgName).getAbsolutePath();
    		new File(path).delete();
    		productImgLocation.getFile().mkdirs();
    		Files.copy(file.getInputStream(), Paths.get(path));
    	}
    	if (category != null) {
    		product.setCategory(categoryService.getOrCreate(category));
    	}
        return productService.save(product, isNew);
    }
    

    @RequestMapping(method = RequestMethod.PUT, consumes = {"application/json"})
    public Product modifyProduct(
    		@RequestBody Product product){
        return productService.save(product, false);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "id/{productId}")
    public void deleteProduct(@PathVariable(value= "productId") String productId){
        productService.delete(productId);
    }

    @RequestMapping(value="page", method = RequestMethod.GET)
    public Page<ProductSearch> findProducts(ProductCriteria criteria, Pageable page){
        return productService.findProducts(criteria, page);
    }
    
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Iterable<ProductSearch> findProductsList(ProductCriteria criteria) {
    	return productService.findProductsList(criteria);
    }
}
