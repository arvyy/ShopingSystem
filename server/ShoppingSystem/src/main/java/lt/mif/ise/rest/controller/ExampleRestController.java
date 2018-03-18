package lt.mif.ise.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lt.mif.ise.ExampleBean;
import lt.mif.ise.domain.Example;
import lt.mif.ise.service.ExampleService;

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
	
	/*
	 * @RequestParam -- HTTP uzklausos parametras. 
	 * Siuo atveju tai butu localhost:8080/rest/example?text=kazkokstekstas
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Example> find(@RequestParam(value = "text", required = false) String text) {
		return text == null? service.getAll() : service.find(text);
	}
	
	/*
	 * @PathVariable -- kelio parametras, t.y. kazkas tarp dvieju '/'
	 * Siuo atveju tai butu localhost:8080/rest/example/text/kazkokstekstas
	 */
	@RequestMapping(value = "text/{text}", method = RequestMethod.GET)
	public Iterable<Example> findAlt(@PathVariable(value = "text", required = false) String text) {
		return find(text);
	}
	
	/*
	 * RequestBody -- POST metodo duomenys; jie neatvaizduojami uzklausos url'e
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody Example example) {
		service.save(example);
	}
	
	/*
	 * Vietoj aibes primityviu lauku, galima paprasyti POJO klases .
	 * Tada requestparametrai sumapinami i to objekto laukus
	 * 
	 * Siuo atveju localhost:8080/rest/example?id=kazkoksid&text=kazkokstext
	 * butu sumapinti i atitinkamai id ir text laukus Example klaseje.
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(Example example) {
		service.delete(example);
	}
	
}
