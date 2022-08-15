package birderbackend.project.rest.dao;

import birderbackend.project.rest.repository.EggRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EggDaoImpl implements EggDao{
    @Autowired
    EggRepository eggRepository;


    public Long deleteEggByBirdBreedingId(Long birdBreedingId) {
        return eggRepository.deleteEggsByBirdBreedingId_Id(birdBreedingId);
    }

//    @Override
//    public Long deleteEggById(Long birdBreedingId) {
//        return eggRepository.deleteEggsByBirdBreedingId_Id(birdBreedingId);
//    }
}
