package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlannerDao {
    Page<Planner> getPlanner(Long affiliation, Pageable pageRequest);

    Page<Planner> getSearchPlannerList(Long affiliation, String planStatus, Long affiliation2, String labelTag, Pageable pageable);

    Planner savePlannerInfo(Planner plannerInfo);
    Optional<Planner> findById(Long id);
}
