package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Bird;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BirdDao {
    Optional<Bird> findById(Long id);

    Page<Bird> getBird(Long affiliation, Pageable page);

    Page<Bird> getSearchBirdList(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page);

    Bird saveBirdInfo(Bird birdInfo);

    Bird getBird(Long id);

//    Long deleteBirdById(Long id);

    Bird getSearchByBirdNameBirdCode(Long affiliation, String birdName, Long affiliation2, String birdCode);

    Bird getSearchByBirdName(Long affiliation, String birdName);

    Bird getSearchByBirdCode(Long affiliation, String birdCode);

    List<Bird> getMaleOrFemaleBirdList(String sexOfBird, Long id);
    List<Bird> getMaleOrFemaleBirdListNoSelf(String sexOfBird, Long affiliation, Long birdId);
    List<Bird> getBirdListWithoutPaging(Long affiliation_id);
    List<Bird> findByBirdSpeciesId_Id(Long birdSpeciesId_id);

}
