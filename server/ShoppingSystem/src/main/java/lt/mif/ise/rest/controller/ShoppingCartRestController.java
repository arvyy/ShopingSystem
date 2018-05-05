package lt.mif.ise.rest.controller;

import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.domain.ProductForCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping("api/shoppingcart/")
@RestController
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCart shoppingCart;

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

    @RequestMapping(method = RequestMethod.GET)
    public ConcurrentHashMap<String, Integer> getCart(){
        return shoppingCart.getCart();
    }
}
