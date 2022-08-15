package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Planner;
import birderbackend.project.rest.repository.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PlannerDaoImpl implements PlannerDao{

    @Autowired
    PlannerRepository plannerRepository;

    @Override
    public Page<Planner> getPlanner(Long affiliation, Pageable pageRequest) {
        return plannerRepository.findByAffiliation_Id(affiliation, pageRequest);
    }
}
