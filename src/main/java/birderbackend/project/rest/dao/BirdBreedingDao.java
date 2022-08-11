package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BirdBreedingDao {
    Optional<BirdBreeding> findById(Long id);

    BirdBreeding getBirdBreeding(Long id);

    Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest);

    Page<BirdBreeding> getSearchBirdBreedingList(Long affiliation, String breedingCageNumber, Long affiliation2, Long haveMale_id, Long affiliation3, Long haveFemale_id, Long affiliation4, String breedingStatus, Pageable pageable);

    BirdBreeding saveBirdBreedingInfo(BirdBreeding birdBreedingInfo);

    BirdBreeding getSearchByMaleFemaleCode(Long affiliation, Long haveMale_id, Long affiliation2, Long haveFemale_id);

    Long deleteBirdBreedingById(Long id);

}
