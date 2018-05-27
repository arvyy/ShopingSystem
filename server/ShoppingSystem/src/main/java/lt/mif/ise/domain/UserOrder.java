package lt.mif.ise.domain;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class UserOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;

    private String email;

    private String state;

    private ArrayList<ProductForCart> products;

    @OneToOne
    private PaymentSuccess payment;

    @Version
    private int version;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<ProductForCart> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductForCart> products) {
        this.products = products;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
