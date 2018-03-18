package lt.mif.ise.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.mif.ise.domain.Example;
import lt.mif.ise.jpa.ExampleRepository;
import lt.mif.ise.service.ExampleService;

@Service //must have anotacija ant servisu
public class ExampleServiceImpl implements ExampleService {
	
	/*
	 * Servisai yra singleton -- cia neturi buti jokio state'o.
	 */

	//servisai naudoja db lygi
	@Autowired ExampleRepository repo;

	//galima naudoti kitus servisus
	//@Autowired ExampleOtherService otherService;
	
	@Override
	public Iterable<Example> getAll() {
		return repo.findAll();
	}
	
	@Override
	public Iterable<Example> find(String text) {
		return repo.findByText(text);
	}

	@Override
	public Example save(Example e) {
		return repo.save(e);
	}

	@Override
	public void delete(Example e) {
		if (e.getId() != null) {
			repo.deleteById(e.getId());
		} else if (e.getText() != null) {
			repo.deleteAll(repo.findByText(e.getText()));
		} else {
			throw new RuntimeException("Invalid request.");
		}
	}	
}
