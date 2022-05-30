package birderbackend.project.rest.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import birderbackend.project.rest.security.entity.Authority;
import birderbackend.project.rest.security.entity.AuthorityName;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(AuthorityName input);
}
