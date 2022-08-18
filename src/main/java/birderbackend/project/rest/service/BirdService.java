package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BirdService {
    Page<Bird> getBird(Long affiliation, Pageable pageable);

    Page<Bird> getSearchBirdList(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page);

    Bird saveBirdInfo(Bird birdInfo);

    Bird getBird(Long id);

//    Long deleteBirdById(Long id);

    Optional<Bird> findById(Long id);

    Bird getSearchByBirdNameBirdCode(Long affiliation, String birdName, Long affiliation2, String birdCode);

    Bird getSearchByBirdName(Long affiliation, String birdName);

    Bird getSearchByBirdCode(Long affiliation, String birdCode);

    List<Bird> getMaleOrFemaleBirdList(String sexOfBird, Long id);
    List<Bird> getMaleOrFemaleBirdListNoSelf(String sexOfBird, Long affiliation, Long birdId);
    List<Bird> getBirdListWithoutPaging(Long affiliation_id);

}
