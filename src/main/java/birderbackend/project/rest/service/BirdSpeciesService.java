package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.BirdSpecies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BirdSpeciesService {
    Page<BirdSpecies> getBirdSpecies(Long affiliation, Pageable pageRequest);
}
