package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FarmDaoImpl implements FarmDao{
    @Autowired
    FarmRepository farmRepository;

    @Override
    public Farm save(Farm farm) {
        return farmRepository.save(farm);
    }

    @Override
    public Page<Farm> getFarm(Pageable pageRequest) {
        return farmRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Farm> findById(Long id) {
        return farmRepository.findById(id);
    }

    public Long deleteFarmById(Long id) {

        return farmRepository.deleteFarmById(id);
    }
}
