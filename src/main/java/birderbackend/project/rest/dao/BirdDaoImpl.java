package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BirdDaoImpl implements BirdDao{

    @Autowired
    BirdRepository birdRepository;

    @Override
    public Optional<Bird> findById(Long id) {
        return birdRepository.findById(id);
    }

    @Override
    public Page<Bird> getBird(Long affiliation, Pageable page) {
        return birdRepository.findByAffiliation_Id(affiliation, page);
    }

    @Override
    public Page<Bird> getSearchBirdList(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page) {
        return birdRepository.findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCaseOrAffiliation_IdAndBirdSpeciesContainingIgnoreCaseOrAffiliation_IdAndBirdStatusContainingIgnoreCase(affiliation, birdName, affiliation2, birdCode, affiliation3, birdSpecies, affiliation4, birdStatus, page);
    }

    @Override
    public Bird saveBirdInfo(Bird birdInfo) {
        return birdRepository.save(birdInfo);
    }

    @Override
    public Bird getBird(Long id) {
        return birdRepository.findById(id).orElse(null);
    }
}
