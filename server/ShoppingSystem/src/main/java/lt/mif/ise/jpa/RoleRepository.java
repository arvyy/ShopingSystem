package lt.mif.ise.jpa;

import lt.mif.ise.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, String> {
    Set<Role> findByName(String name);
}
