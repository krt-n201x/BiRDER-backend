package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.BirdBreedingDao;
import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BirdBreedingServiceImpl implements BirdBreedingService{

    @Autowired
    BirdBreedingDao birdBreedingDao;

    @Override
    public Optional<BirdBreeding> findById(Long affiliation) {
        return birdBreedingDao.findById(affiliation);
    }

    @Override
    public Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest) {
        return birdBreedingDao.getBirdBreeding(affiliation, pageRequest);
    }

    @Override
    public Page<BirdBreeding> getSearchBirdBreedingList(Long affiliation, String breedingCageNumber, Long affiliation2, Long haveMale_id, Long affiliation3, Long haveFemale_id, Long affiliation4, String breedingStatus, Pageable pageable) {
        return birdBreedingDao.getSearchBirdBreedingList(affiliation, breedingCageNumber, affiliation2, haveMale_id, affiliation3, haveFemale_id, affiliation4, breedingStatus, pageable);
    }

    @Override
    public BirdBreeding saveBirdBreedingInfo(BirdBreeding birdBreedingInfo) {
        return birdBreedingDao.saveBirdBreedingInfo(birdBreedingInfo);
    }

    @Override
    public BirdBreeding getSearchByMaleFemaleCode(Long affiliation, Long haveMale_id, Long affiliation2, Long haveFemale_id) {
        return birdBreedingDao.getSearchByMaleFemaleCode(affiliation, haveMale_id, affiliation2, haveFemale_id);
    }
}
