package lt.mif.ise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    @JsonProperty("error")
    public String Error;

    @JsonProperty("message")
    public String Message;
}
