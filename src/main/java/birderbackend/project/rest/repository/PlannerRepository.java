package birderbackend.project.rest.repository;

import birderbackend.project.rest.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner,Long> {
}
