package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payment extends CardInformation implements Serializable {

    @SerializedName("amount")
    @JsonProperty("amount")
    public int Amount;
}