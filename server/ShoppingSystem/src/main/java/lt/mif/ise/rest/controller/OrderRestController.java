package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.UserOrder;
import lt.mif.ise.error.exception.BadRequestException;
import lt.mif.ise.service.OrderService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
@RestController
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @Value("${userOrder.state.values}")
    private String[] orderStates;

    @RequestMapping(method = RequestMethod.POST)
    public UserOrder makeOrder (@RequestBody CardInformation cardInformation){
        return orderService.makeOrder(cardInformation);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserOrder> getAllOrders (){
        return orderService.getAllOrders();
    }

    @RequestMapping(value = "email/{user}", method = RequestMethod.GET)
    public Iterable<UserOrder> getOrdersByUser (@PathVariable(value = "user") String user){
        return orderService.getOrdersByUser(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{orderId}")
    public UserOrder getById (@PathVariable(value = "orderId") String orderId){
        return orderService.getById(orderId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{orderId}")
    public UserOrder updateOrderState (@PathVariable(value = "orderId") String orderId, @RequestParam(value = "state") String state){
        if (!ArrayUtils.contains(orderStates, state))
            throw new BadRequestException(String.format("Invalid order state %s", state));
        return orderService.updateOrder(orderId, state);
    }

    @RequestMapping(method = RequestMethod.GET, value = "states")
    public String[] getStates(){
        return orderStates;
    }
}
