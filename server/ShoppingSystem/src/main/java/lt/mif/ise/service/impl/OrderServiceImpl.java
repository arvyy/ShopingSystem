package lt.mif.ise.service.impl;

import javafx.util.Pair;
import lt.mif.ise.domain.*;
import lt.mif.ise.jpa.OrderRepository;
import lt.mif.ise.jpa.PaymentRepository;
import lt.mif.ise.service.OrderService;
import lt.mif.ise.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

//TODO transactions
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public UserOrder makeOrder(CardInformation cardInformation) {
        Payment payment = (Payment) cardInformation;

        payment.Amount = 0;
        Iterable<Pair<Product, Integer>> cart = cartService.getCart();
        cart.forEach((productAmount) -> {
            Product product = productAmount.getKey();
            Integer amount = productAmount.getValue();
            payment.Amount += product.getPrice() * amount;
        });

        cartService.clearCart();

        PaymentSuccess paymentSuccess = paymentRepo.MakePayment(payment);

        UserOrder order = new UserOrder();
        order.setPayment(paymentSuccess);
        order.setState("NEW");
        order.setUsername(getUserUsername());

        return order;
    }

    @Override
    public Iterable<UserOrder> getAllOrders (){
        return orderRepo.findAll();
    }

    @Override
    public UserOrder getById (String orderId){
        return orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Failed to get order."));
    }

    @Override
    public UserOrder updateOrder(UserOrder order) {
        return orderRepo.save(order);
    }

    // gets current user username
    private String getUserUsername (){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
