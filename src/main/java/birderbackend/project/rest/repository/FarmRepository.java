package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm,Long> {

//    Farm findById(Long farmId);
    Long deleteFarmById(Long id);
}
