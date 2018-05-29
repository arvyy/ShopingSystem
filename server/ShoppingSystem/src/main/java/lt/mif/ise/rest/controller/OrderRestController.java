package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.UserOrder;
import lt.mif.ise.error.exception.BadRequestException;
import lt.mif.ise.service.OrderService;
import lt.mif.ise.service.ShoppingCartService;

import java.security.Principal;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
@RestController
public class OrderRestController {
	
	@Autowired ShoppingCartService cartService;
	
    @Autowired
    private OrderService orderService;

    @Value("${userOrder.state.values}")
    private String[] orderStates;

    @RequestMapping(method = RequestMethod.POST)
    public UserOrder makeOrder (@RequestBody CardInformation cardInformation, Principal p){
       UserOrder order = orderService.makeOrder(p.getName(), cartService.getCart(), cardInformation);
       cartService.clearCart();
       return order;
    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public Iterable<UserOrder> getAllOrders (){
//        return orderService.getAllOrders();
//    }

    @RequestMapping(method = RequestMethod.GET, value="my")
    public Page<UserOrder> getMyOrders(Principal p, Pageable page) {
    	return orderService.getOrdersByUser(p.getName(), page);
    }
    
    @RequestMapping(method = RequestMethod.GET, value="user/{user}")
    public Page<UserOrder> getOrdersByUser (@PathVariable(value = "user") String user, Pageable page){       
        return orderService.getOrdersByUser(user, page);
    }

    @RequestMapping(method = RequestMethod.GET, value = "id/{orderId}")
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
