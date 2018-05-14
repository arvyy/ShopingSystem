package lt.mif.ise.jpa.impl;

import com.google.gson.Gson;
import lt.mif.ise.domain.Error;
import lt.mif.ise.domain.Payment;
import lt.mif.ise.domain.PaymentSuccess;
import lt.mif.ise.jpa.PaymentRepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private Gson gson;
    private HttpClient client;

    @PostConstruct
    public void init(){
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("technologines", "platformos");
        provider.setCredentials(AuthScope.ANY, credentials);

        this.client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        this.gson = new Gson();
    }

    public PaymentSuccess MakePayment (Payment payment) {
        try {
            HttpPost request = new HttpPost("https://mock-payment-processor.appspot.com/v1/payment");
            request.setEntity(new StringEntity(gson.toJson(payment)));

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            String content = IOUtils.toString(entity.getContent(), "UTF8");
            if (statusCode >= 200 && statusCode < 300)
                return gson.fromJson(content, PaymentSuccess.class);
            else {
                Error error = gson.fromJson(content, Error.class);
                throw new RuntimeException(error.Message);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Something went wrong");
        } catch (ClientProtocolException e) {
            throw new RuntimeException("Something went wrong");
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong");
        }
    }
}
