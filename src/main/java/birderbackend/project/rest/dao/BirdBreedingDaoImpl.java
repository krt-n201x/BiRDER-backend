package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.BirdBreeding;
import birderbackend.project.rest.repository.BirdBreedingRepository;
import birderbackend.project.rest.repository.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BirdBreedingDaoImpl implements BirdBreedingDao {

    @Autowired
    BirdBreedingRepository birdBreedingRepository;

    @Override
    public Optional<BirdBreeding> findById(Long id) {
        return birdBreedingRepository.findById(id);
    }

    @Override
    public BirdBreeding getBirdBreeding(Long id) {
        return birdBreedingRepository.findById(id).orElse(null);
    }

    @Override
    public Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest) {
        return birdBreedingRepository.findByAffiliation_Id(affiliation, pageRequest);
    }

    @Override
    public Page<BirdBreeding> getSearchBirdBreedingList(Long affiliation, String breedingCageNumber, Long affiliation2, Long haveMale_id, Long affiliation3, Long haveFemale_id, Long affiliation4, String breedingStatus, Pageable pageable) {
        return birdBreedingRepository.findByAffiliation_IdAndBreedingCageNumberContainingIgnoreCaseOrAffiliation_IdAndHaveMale_IdOrAffiliation_IdAndHaveFemale_IdOrAffiliation_IdAndBreedingStatusContainingIgnoreCase(affiliation, breedingCageNumber, affiliation2, haveMale_id, affiliation3, haveFemale_id, affiliation4, breedingStatus, pageable);
    }

    @Override
    public BirdBreeding saveBirdBreedingInfo(BirdBreeding birdBreedingInfo) {
        return birdBreedingRepository.save(birdBreedingInfo);
    }

    @Override
    public BirdBreeding getSearchByMaleFemaleCode(Long affiliation, Long haveMale_id, Long affiliation2, Long haveFemale_id) {
        return birdBreedingRepository.findByAffiliation_IdAndHaveMale_IdOrAffiliation_IdAndHaveFemale_Id(affiliation, haveMale_id, affiliation2, haveFemale_id);
    }

    @Override
    public Long deleteBirdBreedingById(Long id) {
        return birdBreedingRepository.deleteBirdBreedingById(id);
    }
}
