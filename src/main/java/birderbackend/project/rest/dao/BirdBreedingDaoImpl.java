package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.BirdBreeding;
import birderbackend.project.rest.repository.BirdBreedingRepository;
import birderbackend.project.rest.repository.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BirdBreedingDaoImpl implements BirdBreedingDao {

    @Autowired
    BirdBreedingRepository birdBreedingRepository;

    @Override
    public Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest) {
        return birdBreedingRepository.findByAffiliation_Id(affiliation, pageRequest);
    }
}
