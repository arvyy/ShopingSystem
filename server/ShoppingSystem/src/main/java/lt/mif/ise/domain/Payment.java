package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Payment extends CardInformation {
    @SerializedName("amount")
    @JsonProperty("amount")
    public int Amount;

    public Payment () {}

    public Payment (CardInformation cardInformation, int amount) {
        super (cardInformation.CardNumber,
                cardInformation.CardNumber,
                cardInformation.ExpirationMonth,
                cardInformation.ExpirationYear,
                cardInformation.Cvv);
        this.Amount = amount;
    }
}