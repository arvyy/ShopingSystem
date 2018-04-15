package lt.mif.ise.jpa;

import lt.mif.ise.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
