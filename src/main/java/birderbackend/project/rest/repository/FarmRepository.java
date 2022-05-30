package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm,Long> {

//    Farm findById(Long farmId);
}
