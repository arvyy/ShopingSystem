package lt.mif.ise.service;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.Order;

public interface OrderService {
    Order makeOrder (CardInformation payment);
    Iterable<Order> getAllOrders();
    Order getById (String orderId);
    Order updateOrder (Order order);
}
