package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PaymentSuccess extends Payment {
    @Id
    @JsonProperty("id")
    @SerializedName("id")
    private String id;

    @JsonProperty("created_at")
    @SerializedName("created_at")
    private Date created;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}
}
