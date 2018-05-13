package lt.mif.ise.jpa;

import lt.mif.ise.domain.Category;
import lt.mif.ise.domain.Product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findByName(String name);
    Optional<Product> findByProductId(String productId);
	void deleteByProductId(String productId);
	long countByCategory(Category c);
}
