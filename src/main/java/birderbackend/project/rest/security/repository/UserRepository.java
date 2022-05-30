package birderbackend.project.rest.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import birderbackend.project.rest.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
