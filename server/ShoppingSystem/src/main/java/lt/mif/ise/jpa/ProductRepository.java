package lt.mif.ise.jpa;

import lt.mif.ise.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findByName(String name);
    Optional<Product> findByProductId(String productId);
}
