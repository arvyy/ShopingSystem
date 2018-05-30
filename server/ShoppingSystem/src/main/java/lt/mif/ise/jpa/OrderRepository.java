package lt.mif.ise.jpa;

import lt.mif.ise.domain.User;
import lt.mif.ise.domain.UserOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<UserOrder, String> {
    Page<UserOrder> findByUser(User user, Pageable page);
    Page<Iterable<UserOrder>> findAll(Pageable page);
}
