package lt.mif.ise.service;

import org.javatuples.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.UserOrder;

public interface OrderService {
    UserOrder makeOrder (String user, Iterable<Pair<Product, Integer>> cart, CardInformation payment);
    Page<UserOrder> getAllOrders(Pageable page);
    Page<UserOrder> getOrdersByUser(String user, Pageable page);
    UserOrder getById (String orderId);
    UserOrder updateOrder (String orderId, String state);
}
