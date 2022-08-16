package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.BirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        return birdRepository.findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCaseOrAffiliation_IdAndBirdSpeciesId_SpeciesNameContainingIgnoreCaseOrAffiliation_IdAndBirdStatusContainingIgnoreCase(affiliation, birdName, affiliation2, birdCode, affiliation3, birdSpecies, affiliation4, birdStatus, page);
    }

    @Override
    public Bird saveBirdInfo(Bird birdInfo) {
        return birdRepository.save(birdInfo);
    }

    @Override
    public Bird getBird(Long id) {
        return birdRepository.findById(id).orElse(null);
    }

    @Override
    public Bird getSearchByBirdNameBirdCode(Long affiliation, String birdName, Long affiliation2, String birdCode) {
        return birdRepository.findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCase(affiliation, birdName, affiliation2, birdCode);
    }

    @Override
    public Bird getSearchByBirdName(Long affiliation, String birdName) {
        return birdRepository.findByAffiliation_IdAndBirdNameContainingIgnoreCase(affiliation, birdName);
    }

    @Override
    public Bird getSearchByBirdCode(Long affiliation, String birdCode) {
        return birdRepository.findByAffiliation_IdAndBirdCodeContainingIgnoreCase(affiliation, birdCode);
    }

    @Override
    public List<Bird> getMaleOrFemaleBirdList(String sexOfBird, Long id) {
        return birdRepository.findBySexOfBirdAndAffiliation_Id(sexOfBird, id);
    }

    @Override
    public List<Bird> getMaleOrFemaleBirdListNoSelf(String sexOfBird, Long affiliation, Long birdId) {
        return birdRepository.findBySexOfBirdAndAffiliation_IdAndIdNot(sexOfBird, affiliation, birdId);
    }

    @Override
    public List<Bird> getBirdListWithoutPaging(Long affiliation_id) {
        return birdRepository.findByAffiliation_Id(affiliation_id);
    }

//    public Long deleteBirdById(Long id) {return birdRepository.deleteBirdById(id);}


}
