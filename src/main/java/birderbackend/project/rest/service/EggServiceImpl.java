package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.EggDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EggServiceImpl implements EggService{
    @Autowired
    EggDao eggDao;

    @Transactional
    public List<Long> deleteEggByBirdBreedingId(Long birdBreedingId) {
        return eggDao.deleteEggByBirdBreedingId(birdBreedingId);
    }
}
