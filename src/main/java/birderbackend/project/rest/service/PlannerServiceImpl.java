package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.PlannerDao;
import birderbackend.project.rest.entity.Planner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlannerServiceImpl implements PlannerService{

    @Autowired
    PlannerDao plannerDao;

    @Override
    public Page<Planner> getPlanner(Long affiliation, Pageable pageRequest) {
        return plannerDao.getPlanner(affiliation, pageRequest);
    }

    @Override
    public Page<Planner> getSearchPlannerList(Long affiliation, String planStatus, Long affiliation2, String labelTag, Pageable pageable) {
        return plannerDao.getSearchPlannerList(affiliation, planStatus, affiliation2, labelTag, pageable);
    }
}
