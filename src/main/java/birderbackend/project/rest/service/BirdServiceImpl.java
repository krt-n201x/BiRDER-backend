package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.BirdDao;
import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.security.entity.Authority;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class BirdServiceImpl implements BirdService{

    @Autowired
    BirdDao birdDao;

    @Override
    public Page<Bird> getBird(Long affiliation, Pageable pageable) {
        return birdDao.getBird(affiliation, pageable);
    }

    @Override
    public Page<Bird> getSearchBirdList(Long affiliation, String birdName, Long affiliation2, String birdCode, Long affiliation3, String birdSpecies, Long affiliation4, String birdStatus, Pageable page) {
        return birdDao.getSearchBirdList(affiliation, birdName, affiliation2, birdCode, affiliation3, birdSpecies, affiliation4, birdStatus, page);
    }

    @Override
    @Transactional
    public Bird saveBirdInfo(Bird birdInfo) {
        return birdDao.saveBirdInfo(birdInfo);
    }

    @Override
    public Bird getBird(Long id) {
        return birdDao.getBird(id);
    }

}
