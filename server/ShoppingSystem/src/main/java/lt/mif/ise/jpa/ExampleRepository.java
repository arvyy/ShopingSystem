package lt.mif.ise.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lt.mif.ise.domain.Example;

public interface ExampleRepository extends CrudRepository<Example, String>{

	/*
	 * Izi budas kazka nekomplikuoto daryt su db yra naudot query method.
	 * Pagal metodo pavadinima, springas pakisa implementacija.
	 * 
	 * Daugiau: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details
	 */
	List<Example> findByText(String text);
	
}
