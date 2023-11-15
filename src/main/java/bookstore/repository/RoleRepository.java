package bookstore.repository;

import bookstore.model.Role;
import bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<User, Long> {
    Role findRoleByName(Role.RoleName name);
}
