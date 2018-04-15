package lt.mif.ise.rest.controller;

import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.domain.ProductForCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("api/shoppingcart/")
@Controller
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCart shoppingCart;

//TODO dont pass id and amount, pass the object
    @RequestMapping(method = RequestMethod.POST)
    public Iterable<ProductForCart> addToCart(@RequestBody Iterable<ProductForCart> products){
        for (ProductForCart product: products) {
            shoppingCart.addToCart(product.Id, product.Amount);
        }
        return products;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}")
    public void deleteFromCart(@PathVariable(value = "productId") String productId){
        shoppingCart.removeFromCart(productId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}")
    public Iterable<ProductForCart> deleteFromCart(@PathVariable(value = "productId") Iterable<ProductForCart> products){
        List<ProductForCart> deletedProducts = new ArrayList<ProductForCart>();
        for (ProductForCart product: products) {
            Integer deletedAmount = shoppingCart.removeFromCart(product.Id, product.Amount);
            ProductForCart deletedProduct = new ProductForCart();
            deletedProduct.Id = product.Id;
            deletedProduct.Amount = deletedAmount;
            deletedProducts.add(deletedProduct);
        }
        return deletedProducts;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ConcurrentHashMap<String, Integer> getCart(){
        return shoppingCart.getCart();
    }
}
