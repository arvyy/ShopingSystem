package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PaymentSuccess extends Payment {
    @javax.persistence.Id
    @JsonProperty("id")
    @SerializedName("id")
    public String Id;

    @JsonProperty("created_at")
    @SerializedName("created_at")
    public Date Created;
}
