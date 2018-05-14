package lt.mif.ise.rest.controller;

import javafx.util.Pair;
import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.ProductForCart;

import lt.mif.ise.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/api/shoppingcart")
@RestController
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCartService shoppingCart;

    @RequestMapping(method = RequestMethod.POST)
    public Iterable<Object[]> addToCart(@RequestBody Iterable<ProductForCart> products){
        for (ProductForCart product: products) {
            shoppingCart.addToCart(product.Id, product.Amount);
        }
        //TODO delete
        return getCart();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}")
    public void deleteFromCart(@PathVariable(value = "productId") String productId){
        shoppingCart.removeFromCart(productId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{productId}/{amount}")
    public ProductForCart deleteFromCart
    (
        @PathVariable(value = "productId") String productId,
        @PathVariable(value = "amount") int amount
    ){
        Integer deletedAmount = shoppingCart.removeFromCart(productId, amount);
        ProductForCart deletedProduct = new ProductForCart();
        deletedProduct.Id = productId;
        deletedProduct.Amount = deletedAmount;
        return deletedProduct;
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public void clearCart() {
    	shoppingCart.clearCart();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Object[]> getCart(){
        Iterable<Pair<Product, Integer>> cart = shoppingCart.getCart();
        ArrayList<Object[]> forDeserializing = new ArrayList<>();
        cart.forEach((pair) -> {
            forDeserializing.add(new Object[]{
                    pair.getKey(), pair.getValue()
            });
        });
        return forDeserializing;
    }

    //test method
    //TODO delete before PROD
/*
 * [[product, count], [product2, count2], ...]
 */
    @RequestMapping(method = RequestMethod.GET, value = "/testingData")
    public Iterable<Object[]> getCartTestingData(){
        //return shoppingCart.getCart();
    	return IntStream.range(0, 5).mapToObj(i -> {
    		Product p = new Product();
    		p.setProductId("prod-" + i);
    		p.setName("Randomname" + p.hashCode());
    		p.setPrice(199);
    		return new Object[]{
    			p, i
    		};
    	}).collect(Collectors.toList());
    }
}
