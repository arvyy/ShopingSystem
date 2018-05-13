package lt.mif.ise;

import java.util.UUID;

import lt.mif.ise.bean.ExportImport;
import lt.mif.ise.bean.ExportImportImpl;
import lt.mif.ise.bean.ShoppingCart;
import lt.mif.ise.bean.ShoppingCartImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/*
 * Sita anotacija apjungia kelis dalykus:
 * 	1. Nuo jos path daromas komponentu scanas (todel visi komponentai turi but sitam arba gilesniam pakete)
 * 	   Komponentas yra @Component, bei ji prapleciancios kitos anotacijos, tarp ju @RestController, @Service, @Repository
 * 	   Kiekviena atrasta komponenta galima autowire'inti (dazniausiai pagal jo interface'a)
 *  2. Elgiasi kaip konfiguracija -- kiekvienas metodas anotuotas @Bean grazina koki tais objekta. Ta objekta
 *     tada galima autowire'inti pagal jo tipa.
 *     
 *  Visais atvejais autowirinant, turi buti tinkamas 1 objektas. Jei tinka daugiau, reikia specifikuoti pagal varda, arba
 *  @Primary anotacija. Jei netinka nei vienas, reikia nurodyt kad neprivalomas, kitaip mes klaida. Bet situ
 *  situaciju manau nebus pas mus, kada projektas ganetinai paprastas.
 */
@SpringBootApplication
public class ShoppingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingSystemApplication.class, args);
	}
	
	/*
	 * Nurodom @SessionScope. Su situo metodu, kiekvienai sesijai bus sukurtas atskiras ExampleBean
	 */
	@Bean
	@SessionScope(proxyMode=ScopedProxyMode.INTERFACES)
	public ExampleBean exampleBean() {
		ExampleBean b = new ExampleBeanImpl();
		b.setData(UUID.randomUUID().toString());
		return b;
	}

	@Bean
    @SessionScope(proxyMode=ScopedProxyMode.INTERFACES)
    public ShoppingCart ShoppingCartBean(){
	    return new ShoppingCartImp();
    }

	@Bean
	@RequestScope
	public ExportImport ExportImportBean() { return new ExportImportImpl(); }
}
