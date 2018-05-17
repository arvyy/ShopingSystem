package lt.mif.ise.service.impl;

import javafx.util.Pair;
import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.domain.*;
import lt.mif.ise.jpa.OrderRepository;
import lt.mif.ise.jpa.PaymentRepository;
import lt.mif.ise.jpa.PaymentSuccessRepository;
import lt.mif.ise.service.OrderService;
import lt.mif.ise.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//TODO transactions
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private ShoppingCart cart;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private PaymentSuccessRepository paymentSuccessRepo;

    @Override
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
    public UserOrder getById (String orderId){
        return orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Failed to get order."));
    }

    @Override
    public UserOrder updateOrder(String orderId, String state) {
        UserOrder order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Failed to get order."));
        order.setState(state);
        return orderRepo.save(order);
    }

    // gets current user email
    private String getUserUsername (){
        return "asd";//((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
