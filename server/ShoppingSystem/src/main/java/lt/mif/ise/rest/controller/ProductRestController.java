package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.search.ProductCriteria;
import lt.mif.ise.domain.search.ProductSearch;
import lt.mif.ise.error.exception.BadRequestException;
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

    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public Product createProduct(
    		Product product,
    		@RequestParam(value = "isNew") boolean isNew, 
    		@RequestParam(value = "categoryName", required=false) String category,
    		@RequestParam(value = "file", required=false) MultipartFile file) throws IllegalStateException, IOException{
        if (!productService.isProductIdValid(product.getProductId())){
            throw new BadRequestException("Invalid product ID specified.");
        }
        product = setImage(product, file);
    	if (category != null) {
    		product.setCategory(categoryService.getOrCreate(category));
    	}
        return productService.save(product, isNew);
    }

    private Product saveProduct(Product product){
        return productService.save(product, false);
    }

    private Product setImage(Product product, MultipartFile file) throws IOException{
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
        return product;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, consumes = {"application/json"})
    public Product modifyProductJson(
    		@RequestBody Product product){
        return saveProduct(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public Product modifyProductMultipart(
            Product product,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "categoryName", required=false) String category,
            @RequestParam(value = "deleteImage", required = false) boolean deleteImage) throws IOException{
        product = setImage(product, file);
        if (file == null && deleteImage){
            Product temp = productService.getById(product.getProductId());
            if (null == temp){
                throw new BadRequestException(String.format("Product with %s ID not found", product.getProductId()));
            }
            String imageUrl = temp.getImageUrl();
            String imageName = "";
            if (null != imageUrl){
                imageName = imageUrl.substring(imageUrl.lastIndexOf("/"));
            }
            String path = new File(productImgLocation.getFile(), imageName).getAbsolutePath();
            new File(path).delete();
            product.setImageUrl(null);
        }
        if (category != null) {
            product.setCategory(categoryService.getOrCreate(category));
        }
        return saveProduct(product);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
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
