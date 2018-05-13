package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PaymentSuccess extends Payment {
    @JsonProperty("id")
    @SerializedName("id")
    public String Id;

    @JsonProperty("created_at")
    @SerializedName("created_at")
    public Date Created;
}
