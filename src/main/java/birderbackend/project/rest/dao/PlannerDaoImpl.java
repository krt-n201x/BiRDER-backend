package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Planner;
import birderbackend.project.rest.repository.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlannerDaoImpl implements PlannerDao{

    @Autowired
    PlannerRepository plannerRepository;

    @Override
    public Page<Planner> getPlanner(Long affiliation, Pageable pageRequest) {
        return plannerRepository.findByAffiliation_Id(affiliation, pageRequest);
    }

    @Override
    public Page<Planner> getSearchPlannerList(Long affiliation, String planStatus, Long affiliation2, String labelTag, Pageable pageable) {
        return plannerRepository.findByAffiliation_IdAndPlanStatusContainingIgnoreCaseOrAffiliation_IdAndLabelTagContainingIgnoreCase(affiliation, planStatus, affiliation2, labelTag, pageable);
    }

    @Override
    public Planner savePlannerInfo(Planner plannerInfo) {
        return plannerRepository.save(plannerInfo);
    }

    @Override
    public Optional<Planner> findById(Long id) {
        return plannerRepository.findById(id);
    }

    @Override
    public Planner getPlanner(Long id) {
        return plannerRepository.findById(id).orElse(null);
    }

    @Override
    public Long deletePlannerById(Long id) {
        return plannerRepository.deletePlannerById(id);
    }
}
