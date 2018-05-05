package lt.mif.ise.jpa;

import lt.mif.ise.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, String>, JpaRepository<User, String> {
    User findByEmail(String email);
}
