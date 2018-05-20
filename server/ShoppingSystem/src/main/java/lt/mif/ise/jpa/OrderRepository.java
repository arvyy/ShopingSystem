package lt.mif.ise.jpa;

import lt.mif.ise.domain.UserOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<UserOrder, String> {
    Optional<Iterable<UserOrder>> findByEmail(String email);
}
