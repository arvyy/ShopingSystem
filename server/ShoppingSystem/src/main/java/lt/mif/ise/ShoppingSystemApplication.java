package lt.mif.ise;

import lt.mif.ise.bean.ExportImport;
import lt.mif.ise.bean.ExportImportImpl;
import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.bean.ShoppingCartImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@SpringBootApplication
public class ShoppingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingSystemApplication.class, args);
	}
	@Bean
    @SessionScope(proxyMode=ScopedProxyMode.INTERFACES)
    public ShoppingCart ShoppingCartBean(){
	    return new ShoppingCartImp();
    }

	@Bean
	@RequestScope
	public ExportImport ExportImportBean() { return new ExportImportImpl(); }
	

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
