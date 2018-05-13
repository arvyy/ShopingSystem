package lt.mif.ise.service.impl;

import javafx.util.Pair;
import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.Payment;
import lt.mif.ise.domain.Product;
import lt.mif.ise.jpa.PaymentRepository;
import lt.mif.ise.service.OrderService;
import lt.mif.ise.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO transactions
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private PaymentRepository paymentRepo;

    @Override
    public void makeOrder(CardInformation cardInformation) {
        Payment payment = (Payment) cardInformation;
        payment.Amount = 0;

        Iterable<Pair<Product, Integer>> cart = cartService.getCart();
        cart.forEach((productAmount) -> {
            Product product = productAmount.getKey();
            Integer amount = productAmount.getValue();
            payment.Amount += product.getPrice() * amount;
        });

        paymentRepo.MakePayment(payment);
    }
}
