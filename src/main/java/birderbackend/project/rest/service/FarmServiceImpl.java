package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.FarmDao;
import birderbackend.project.rest.entity.Farm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FarmServiceImpl implements FarmService{

    @Autowired
    FarmDao farmDao;

    @Override
    public Optional<Farm> findById(Long id) {
        return farmDao.findById(id);
    }

    @Transactional
    public Long deleteFarmById(Long id) {
        return farmDao.deleteFarmById(id);
    }
}
