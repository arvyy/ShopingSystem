package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Payment extends CardInformation {
    @SerializedName("amount")
    @JsonProperty("amount")
    public int Amount;

    public Payment () {}

    public Payment (CardInformation cardInformation, int amount) {
        super (cardInformation.CardNumber,
                cardInformation.CardHolder,
                cardInformation.ExpirationMonth,
                cardInformation.ExpirationYear,
                cardInformation.Cvv);
        this.Amount = amount;
    }
}