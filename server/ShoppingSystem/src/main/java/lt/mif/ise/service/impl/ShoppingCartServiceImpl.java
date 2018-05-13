package lt.mif.ise.service.impl;

import javafx.util.Pair;
import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.domain.Product;
import lt.mif.ise.service.ProductService;
import lt.mif.ise.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCart shoppingCart;

    @Override
    public Iterable<Pair<Product, Integer>> getCart() {
        ArrayList<Pair<Product, Integer>> cartWithProducts = new ArrayList<Pair<Product, Integer>>();
        ConcurrentHashMap<String, Integer> cart = shoppingCart.getCart();
        for (String productId : cart.keySet()) {
            Product product = productService.getById(productId);
            Pair<Product, Integer> pair = new Pair<>(product, cart.get(productId));
            cartWithProducts.add(pair);
        }
        return cartWithProducts;
    }

    @Override
    public Integer addToCart(String productId, Integer amount) {
        return shoppingCart.addToCart(productId, amount);
    }

    @Override
    public Integer removeFromCart(String productId, Integer amount) {
        return shoppingCart.removeFromCart(productId, amount);
    }

    @Override
    public void removeFromCart(String productId) {
        shoppingCart.removeFromCart(productId);
    }

    @Override
    public void clearCart() {
        shoppingCart.clearCart();
    }
}
