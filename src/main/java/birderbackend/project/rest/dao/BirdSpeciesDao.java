package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.BirdSpecies;
import birderbackend.project.rest.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BirdSpeciesDao {
    Page<BirdSpecies> getBirdSpecies(Long affiliation, Pageable pageRequest);
}
