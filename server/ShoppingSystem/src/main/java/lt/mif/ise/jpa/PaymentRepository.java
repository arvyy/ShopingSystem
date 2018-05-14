package lt.mif.ise.jpa;

import lt.mif.ise.domain.Payment;
import lt.mif.ise.domain.PaymentSuccess;

public interface PaymentRepository {
    PaymentSuccess MakePayment (Payment payment);
}
