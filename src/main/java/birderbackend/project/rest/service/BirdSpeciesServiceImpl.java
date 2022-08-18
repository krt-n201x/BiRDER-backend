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
}
