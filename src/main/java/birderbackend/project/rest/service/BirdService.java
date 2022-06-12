package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BirdService {
    Page<Bird> getBird(Long affiliation, Pageable pageable);

    Page<Bird> getSearchBirdList(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page);

    Bird saveBirdInfo(Bird birdInfo);

    Bird getBird(Long id);

    Long deleteBirdById(Long id);
}
