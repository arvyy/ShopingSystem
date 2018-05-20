package lt.mif.ise.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

//TODO think of a better name for this
@Entity
public class ProductForCart implements Serializable {
    @Id
    public String Id;
    public int Amount;
}
