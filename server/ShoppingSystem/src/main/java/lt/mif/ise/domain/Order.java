package lt.mif.ise.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    private String username;

    private String state;

    private PaymentSuccess payment;

    public String getId() {
        return id;
    }

    public PaymentSuccess getPayment() {
        return payment;
    }

    public void setPayment(PaymentSuccess payment) {
        this.payment = payment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
