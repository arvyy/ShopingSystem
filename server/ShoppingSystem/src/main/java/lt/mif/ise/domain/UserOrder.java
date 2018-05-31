package lt.mif.ise.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserOrder {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    
    private String state;

    @OneToMany(mappedBy="userOrder", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<UserOrderItem> products;
    
    @ManyToOne(cascade=CascadeType.ALL)
    @JsonIgnore
    private User user;

	private String address;

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

    public List<UserOrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<UserOrderItem> products) {
        this.products = products;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAddress(String a) {
		this.address = a;
	}

	public String getAddress() {
		return this.address;
	}
}
