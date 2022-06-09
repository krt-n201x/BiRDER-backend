package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.BirdStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdStatusRepository extends JpaRepository<BirdStatus,Long> {
}
