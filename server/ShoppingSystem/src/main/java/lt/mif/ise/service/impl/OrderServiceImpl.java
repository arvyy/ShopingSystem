package lt.mif.ise.service.impl;

import org.javatuples.Pair;
import lt.mif.ise.domain.*;
import lt.mif.ise.error.exception.NotFoundException;
import lt.mif.ise.error.exception.UnauthorizedException;
import lt.mif.ise.jpa.OrderRepository;
import lt.mif.ise.jpa.PaymentRepository;
import lt.mif.ise.jpa.PaymentSuccessRepository;
import lt.mif.ise.service.OrderService;
import lt.mif.ise.service.ProductService;
import lt.mif.ise.service.ShoppingCartService;
import lt.mif.ise.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.PostConstruct;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private PaymentSuccessRepository paymentSuccessRepo;

    @Autowired
    private ShoppingCartService cartService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    ProductService ps; //testavimui
    
    @Value("${userOrder.state.first}")
    String startingState;

    
    @Override
    @Transactional
    public UserOrder makeOrder(String userEmail, Iterable<Pair<Product, Integer>> cart, CardInformation cardInformation) {
        Payment payment = new Payment(cardInformation, 0);
        User u = userService.findByEmail(userEmail);
        ArrayList<UserOrderItem> products = new ArrayList<>();
        UserOrder order = new UserOrder();
        cart.forEach((productAmountPair) -> {
        	UserOrderItem i = new UserOrderItem();
        	i.setCount(productAmountPair.getValue1());
        	Product p = productAmountPair.getValue0();
        	i.setName(p.getName());
        	i.setPrice(p.getPrice());
        	i.setProductId(p.getProductId());
        	i.setUserOrder(order);
        	i.setId(UUID.randomUUID().toString());
            products.add(i);
            Product product = productAmountPair.getValue0();
            Integer amount = productAmountPair.getValue1();
            payment.Amount += product.getPrice().doubleValue()* 100 * amount;
        });
        order.setProducts(products);

        PaymentSuccess paymentSuccess = paymentRepo.MakePayment(payment);
        paymentSuccess = paymentSuccessRepo.save(paymentSuccess);

        order.setPayment(paymentSuccess);
        order.setState(startingState);
        order.setUser(u);


        cartService.clearCart();
        return orderRepo.save(order);
    }

    @Override
    public Page<UserOrder> getAllOrders (Pageable page){
        return orderRepo.findAllByOrderByPaymentCreatedDesc(page);
    }

    @Override
    public Page<UserOrder> getOrdersByUser(String email, Pageable page) {
    	User user = userService.findByEmail(email);
        return orderRepo.findByUser(user, page);
    }

    @Override
    public UserOrder getById (String orderId){
        return orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order %s not found", orderId)));
    }

    @Override
    public UserOrder updateOrder(String orderId, String state) {
        UserOrder order = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order %s not found", orderId)));
        order.setState(state);
        return orderRepo.save(order);
    }
}
