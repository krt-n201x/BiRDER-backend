package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.BirdSpecies;
import birderbackend.project.rest.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirdSpeciesRepository extends JpaRepository<BirdSpecies,Long> {
    Page<BirdSpecies> findByAffiliation_Id(Long affiliation, Pageable pageRequest);
    Page<BirdSpecies> findByAffiliation_IdAndSpeciesNameContainingIgnoreCase(Long affiliation_id, String speciesName, Pageable pageable);
    List<BirdSpecies> findByAffiliation_Id(Long affiliation_id);
    Long deleteBirdSpeciesById(Long id);
}
