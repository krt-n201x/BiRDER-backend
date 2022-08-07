package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Bird;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirdRepository extends JpaRepository<Bird,Long> {
    Page<Bird> findByAffiliation_Id(Long affiliation, Pageable pageRequest);

    Page<Bird> findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCaseOrAffiliation_IdAndBirdSpeciesId_SpeciesNameContainingIgnoreCaseOrAffiliation_IdAndBirdStatusContainingIgnoreCase(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page);

    Bird findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCase(Long affiliation, String birdName, Long affiliation2, String birdCode);

//    Long deleteBirdById(Long id);

    Bird findByAffiliation_IdAndBirdNameContainingIgnoreCase(Long affiliation, String birdName);
    Bird findByAffiliation_IdAndBirdCodeContainingIgnoreCase(Long affiliation, String birdCode);

    List<Bird> findBySexOfBirdAndAffiliation_Id(String sexOfBird, Long id);

    List<Bird> findBySexOfBirdAndAffiliation_IdAndIdNot(String sexOfBird, Long affiliation, Long birdId);

}
