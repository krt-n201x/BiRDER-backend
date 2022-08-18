package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.BirdSpeciesDao;
import birderbackend.project.rest.entity.BirdSpecies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BirdSpeciesServiceImpl implements BirdSpeciesService{

    @Autowired
    BirdSpeciesDao birdSpeciesDao;

    @Override
    public Page<BirdSpecies> getBirdSpecies(Long affiliation, Pageable pageRequest) {
        return birdSpeciesDao.getBirdSpecies(affiliation, pageRequest);
    }

    @Override
    public Page<BirdSpecies> getSearchSpeciesList(Long affiliation_id, String speciesName, Pageable pageable) {
        return birdSpeciesDao.getSearchSpeciesList(affiliation_id, speciesName, pageable);
    }

    @Override
    public BirdSpecies saveBirdSpeciesInfo(BirdSpecies birdSpeciesInfo) {
        return birdSpeciesDao.saveBirdSpeciesInfo(birdSpeciesInfo);
    }
}
