package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FarmDao {
    Farm save(Farm farm);

    Page<Farm> getFarm(Pageable pageRequest);

    Optional<Farm> findById(Long id);

    Long deleteFarmById(Long id);
}
