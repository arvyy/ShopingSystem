package lt.mif.ise.jpa;

import lt.mif.ise.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
}
