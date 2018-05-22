package lt.mif.ise.service;

import org.javatuples.Pair;
import lt.mif.ise.domain.Product;

public interface ShoppingCartService {
    Iterable<Pair<Product, Integer>> getCart();
    Integer addToCart (String productId, Integer amount);
    Integer removeFromCart (String productId, Integer amount);
    void removeFromCart (String productId);
    void clearCart();
}
