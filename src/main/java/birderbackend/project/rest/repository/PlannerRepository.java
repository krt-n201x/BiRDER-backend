package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner,Long> {
    Page<Planner> findByAffiliation_Id(Long affiliation, Pageable pageRequest);
    Page<Planner> findByAffiliation_IdAndPlanStatusContainingIgnoreCaseOrAffiliation_IdAndLabelTagContainingIgnoreCase(Long affiliation, String planStatus, Long affiliation2, String labelTag, Pageable pageable);
}
