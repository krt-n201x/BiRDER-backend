package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BirdBreedingDao {
    Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest);
}
