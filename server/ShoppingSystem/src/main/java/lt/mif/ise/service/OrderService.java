package lt.mif.ise.service;

import lt.mif.ise.domain.CardInformation;
import lt.mif.ise.domain.Payment;

public interface OrderService {
    void makeOrder (CardInformation payment);
}
