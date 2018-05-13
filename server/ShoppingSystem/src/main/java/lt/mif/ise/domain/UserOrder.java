package lt.mif.ise.domain;

import javax.persistence.*;

@Entity
public class UserOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    private String username;

    private String state;

    private PaymentSuccess payment;

    public PaymentSuccess getPayment() {
        return payment;
    }

    public void setPayment(PaymentSuccess payment) {
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
