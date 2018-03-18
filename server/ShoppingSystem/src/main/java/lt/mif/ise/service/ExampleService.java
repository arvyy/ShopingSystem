package lt.mif.ise.service;

import lt.mif.ise.domain.Example;

public interface ExampleService {

	Iterable<Example> getAll();
	
	Iterable<Example> find(String text);
	
	Example save(Example e);
	
	void delete(Example e);
	
}
