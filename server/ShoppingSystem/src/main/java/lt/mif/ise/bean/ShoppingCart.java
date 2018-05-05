package lt.mif.ise.bean;

import java.util.concurrent.ConcurrentHashMap;

public interface ShoppingCart {
    ConcurrentHashMap<String, Integer> getCart();
    Integer addToCart (String productId, Integer amount);
    Integer removeFromCart (String productId, Integer amount);
    void removeFromCart (String productId);
}
