package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Bird;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdRepository extends JpaRepository<Bird,Long> {
}
