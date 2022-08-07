package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.BirdBreeding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdBreedingRepository extends JpaRepository<BirdBreeding,Long> {
    Page<BirdBreeding> findByAffiliation_Id(Long affiliation, Pageable pageRequest);
}
