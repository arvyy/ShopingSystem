package lt.mif.ise.jpa;

import lt.mif.ise.domain.UserOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<UserOrder, String> {
}
