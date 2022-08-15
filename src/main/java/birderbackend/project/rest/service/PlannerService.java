package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlannerService {
    Page<Planner> getPlanner(Long affiliation, Pageable pageRequest);
}
