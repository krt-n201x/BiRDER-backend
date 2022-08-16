package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlannerService {
    Page<Planner> getPlanner(Long affiliation, Pageable pageRequest);
    Page<Planner> getSearchPlannerList(Long affiliation, String planStatus, Long affiliation2, String labelTag, Pageable pageable);
    Planner savePlannerInfo(Planner plannerInfo);

}
