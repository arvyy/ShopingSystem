package lt.mif.ise.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import lt.mif.ise.domain.search.ProductSearch;

public interface ProductSearchRepository extends CrudRepository<ProductSearch, String>, JpaSpecificationExecutor<ProductSearch>{
    Page<ProductSearch> findAll(Pageable page);
}
