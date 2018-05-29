package lt.mif.ise.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

public class ProductForCart implements Serializable {
    public String Id;
    public int Amount;
}
