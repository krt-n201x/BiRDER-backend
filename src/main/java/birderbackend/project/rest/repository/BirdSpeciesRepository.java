package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.BirdSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdSpeciesRepository extends JpaRepository<BirdSpecies,Long> {
}
