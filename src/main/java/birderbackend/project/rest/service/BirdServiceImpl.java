package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.BirdDao;
import birderbackend.project.rest.entity.Bird;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BirdServiceImpl implements BirdService{

    @Autowired
    BirdDao birdDao;

    @Override
    public Page<Bird> getBird(Long affiliation, Pageable pageable) {
        return birdDao.getBird(affiliation, pageable);
    }

    @Override
    public Page<Bird> getSearchBirdList(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page) {
        return birdDao.getSearchBirdList(affiliation, birdName, affiliation2, birdCode, affiliation3, birdSpecies, affiliation4, birdStatus, page);
    }
}
