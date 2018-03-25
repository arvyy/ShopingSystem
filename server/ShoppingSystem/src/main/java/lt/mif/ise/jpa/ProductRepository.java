package lt.mif.ise.jpa;

import lt.mif.ise.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String>{
    List<Product> findByName(String name);
}
