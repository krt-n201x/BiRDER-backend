package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdBreedingRepository extends JpaRepository<BirdBreeding,Long> {
    Page<BirdBreeding> findByAffiliation_Id(Long affiliation, Pageable pageRequest);

    Page<BirdBreeding> findByAffiliation_IdAndBreedingCageNumberContainingIgnoreCaseOrAffiliation_IdAndHaveMale_IdOrAffiliation_IdAndHaveFemale_IdOrAffiliation_IdAndBreedingStatusContainingIgnoreCase(Long affiliation, String breedingCageNumber, Long affiliation2, Long haveMale_id, Long affiliation3, Long haveFemale_id, Long affiliation4, String breedingStatus, Pageable pageable);

    BirdBreeding findByAffiliation_IdAndHaveMale_IdOrAffiliation_IdAndHaveFemale_Id(Long affiliation, Long haveMale_id, Long affiliation2, Long haveFemale_id);

//    BirdBreeding findByAffiliation_IdAndHaveMale

    Long deleteBirdBreedingById(Long id);
}
