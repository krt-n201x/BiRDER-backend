package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Egg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EggRepository extends JpaRepository<Egg,Long> {
}
