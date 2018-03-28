package lt.mif.ise.rest.controller;

import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("api/shoppingcart/")
@Controller
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCart shoppingCart;
//    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}")
//    public void deleteProduct(@PathVariable(value= "productId") String productId){
//        productService.delete(productId);
//    }

    //this is stupid but my head doesnt work
    @RequestMapping(method = RequestMethod.POST)
    public Iterable<Product> addToCart(@RequestBody Iterable<Product> productList){
        for (Product product: productList) {
            shoppingCart.addToCart(product.getId(), 1);
        }
        return productList;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}")
    public void deleteFromCart(@PathVariable(value = "productId") String productId){
        shoppingCart.removeFromCart(productId);
    }
    //TODO add cart crud here
}
