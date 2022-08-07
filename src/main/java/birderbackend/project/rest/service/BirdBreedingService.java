package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BirdBreedingService {
    Page<BirdBreeding> getBirdBreeding(Long affiliation, Pageable pageRequest);
}
