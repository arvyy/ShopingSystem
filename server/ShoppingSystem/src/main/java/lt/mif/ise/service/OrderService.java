package lt.mif.ise.service;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.UserOrder;

public interface OrderService {
    UserOrder makeOrder (CardInformation payment);
    Iterable<UserOrder> getAllOrders();
    Iterable<UserOrder> getOrdersByUser(String user);
    UserOrder getById (String orderId);
    UserOrder updateOrder (String orderId, String state);
}
