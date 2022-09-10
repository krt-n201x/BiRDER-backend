package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Egg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EggRepository extends JpaRepository<Egg,Long> {

    Long deleteEggsByBirdBreedingId_Id(Long birdBreedingId);
}
