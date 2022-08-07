package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.BirdBreedingDao;
import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BirdBreedingServiceImpl implements BirdBreedingService{

    @Autowired
    BirdBreedingDao birdBreedingDao;

    @Override
    public Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest) {
        return birdBreedingDao.getBirdBreeding(affiliation, pageRequest);
    }
}
