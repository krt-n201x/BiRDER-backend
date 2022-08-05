package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdBreedingRepository extends JpaRepository<BirdBreeding,Long> {
}
