package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Bird;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdRepository extends JpaRepository<Bird,Long> {
    Page<Bird> findByAffiliation_Id(Long affiliation, Pageable pageRequest);

    Page<Bird> findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCaseOrAffiliation_IdAndBirdSpeciesContainingIgnoreCaseOrAffiliation_IdAndBirdStatusContainingIgnoreCase(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page);

    Bird findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCase(Long affiliation, String birdName, Long affiliation2, String birdCode);
}
