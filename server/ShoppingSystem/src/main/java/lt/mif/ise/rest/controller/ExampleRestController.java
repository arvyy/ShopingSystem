package lt.mif.ise.rest.controller;

import com.google.gson.Gson;
import lt.mif.ise.domain.Error;
import lt.mif.ise.domain.Payment;
import lt.mif.ise.domain.PaymentSuccess;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import lt.mif.ise.ExampleBean;
import lt.mif.ise.domain.Example;
import lt.mif.ise.service.ExampleService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/*
 * bendras path visam controlleriui.
 * Visa, ka grazina @RestController yra convertuojama i JSON
 */
@RequestMapping("/rest/example")
@RestController
public class ExampleRestController {

	/*
	 * Servisus pasijungiam per jo interface'a
	 * Servisai yra singleton -- visos uzklausos eina per viena instance
	 */
	@Autowired ExampleService service;
	
	/*
	 * Sitas beansas yra sesijos scope -- sitoje vietoje springas 
	 * pakisa kiekvienai sesijai jos asmenini instance.
	 * (Tai suveikia, nes @Autowire'inamas yra interface. Jis realizuojamas springo proxiu, 
	 * kuris kuria ir deleguoja metodo uzklausas i ExampleBeanImpl)
	 * Sesijos scope nustatomas configuracijoje -- ShoppingSystemApplication.java
	 */
	@Autowired ExampleBean sessionBean;
	
	/*
	 * Value -- uzklausos kelias; jis prisideda prie RestController kelio.
	 * T.y. pilnas kelias sitam metodui yra /rest/example/sessionBean
	 */
	@RequestMapping(method = RequestMethod.GET, value = "sessionBean")
	public String getSessionBean() {
		return sessionBean.getName() + ":::" + sessionBean.getData();
	}
	
	// TODO exception handling
	@ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid payment information")
    @RequestMapping(method = RequestMethod.POST)
	public PaymentSuccess find(@RequestBody Payment payment) {
        try {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("technologines", "platformos");
            provider.setCredentials(AuthScope.ANY, credentials);

            HttpClient client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .build();

            HttpPost request = new HttpPost("https://mock-payment-processor.appspot.com/v1/payment");

            Gson gson = new Gson();
            String json = gson.toJson(payment);

            request.setEntity(new StringEntity(json));

            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            String content = IOUtils.toString(entity.getContent(), "UTF8");
            if (statusCode >= 200 && statusCode < 300)
                return gson.fromJson(content, PaymentSuccess.class);
            else {
                Error error = gson.fromJson(content, Error.class);


                String asd = "";


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
