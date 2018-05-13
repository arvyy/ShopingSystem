package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.io.Serializable;

public class Payment extends CardInformation {

    @SerializedName("amount")
    @JsonProperty("amount")
    public int Amount;
}