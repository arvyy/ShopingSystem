package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PaymentSuccess extends Payment {
    @JsonProperty("id")
    public String Id;

    @JsonProperty("created_at")
    public Date Created;
}
