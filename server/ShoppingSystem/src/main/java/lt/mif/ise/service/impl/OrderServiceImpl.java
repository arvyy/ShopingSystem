package lt.mif.ise.service.impl;

import javafx.util.Pair;
import lt.mif.ise.domain.*;
import lt.mif.ise.error.exception.NotFoundException;
import lt.mif.ise.error.exception.UnauthorizedException;
import lt.mif.ise.jpa.OrderRepository;
import lt.mif.ise.jpa.PaymentRepository;
import lt.mif.ise.jpa.PaymentSuccessRepository;
import lt.mif.ise.service.OrderService;
import lt.mif.ise.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private PaymentSuccessRepository paymentSuccessRepo;

    @Override
    @Transactional
    public UserOrder makeOrder(CardInformation cardInformation) {
        Payment payment = new Payment(cardInformation, 0);

        Iterable<Pair<Product, Integer>> cart = cartService.getCart();
        ArrayList<ProductForCart> products = new ArrayList<>();
        cart.forEach((productAmountPair) -> {
            ProductForCart productForCart = new ProductForCart();
            productForCart.Id = productAmountPair.getKey().getId();
            productForCart.Amount = productAmountPair.getValue();
            products.add(productForCart);
            Product product = productAmountPair.getKey();
            Integer amount = productAmountPair.getValue();
            payment.Amount += product.getPrice().doubleValue()* 100 * amount;
        });

        cartService.clearCart();

        PaymentSuccess paymentSuccess = paymentRepo.MakePayment(payment);
        paymentSuccessRepo.save(paymentSuccess);

        UserOrder order = new UserOrder();
        order.setPayment(paymentSuccess);
        order.setState("NEW");
        order.setEmail(getUserUsername());
        order.setProducts(products);

        orderRepo.save(order);

        return order;
    }

    @Override
    public Iterable<UserOrder> getAllOrders (){
        return orderRepo.findAll();
    }

    @Override
    public Iterable<UserOrder> getOrdersByUser(String user) {
        return orderRepo.findByEmail(user).orElseThrow(() ->new NotFoundException(String.format("User %s not found", user)));
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

    private String getUserUsername (){
        try {
            return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        }
        catch (Exception ex){
            throw new UnauthorizedException("Please log in");
        }
    }
}
