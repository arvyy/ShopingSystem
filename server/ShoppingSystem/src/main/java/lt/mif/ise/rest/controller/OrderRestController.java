package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.Order;
import lt.mif.ise.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order/")
@RestController
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public Order makeOrder (CardInformation cardInformation){
        return orderService.makeOrder(cardInformation);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Order> getAllOrders (){
        return orderService.getAllOrders();
    }

    @RequestMapping(method = RequestMethod.GET, value = {"orderId"})
    public Order getById (@PathVariable(value = "orderId") String orderId){
        return orderService.getById(orderId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Order updateOrder (Order order){
        return orderService.updateOrder(order);
    }
}
