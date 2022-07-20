package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.FarmDao;
import birderbackend.project.rest.dao.UserDao;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.FarmRepository;
import birderbackend.project.rest.security.entity.Authority;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    UserDao userDao;
    @Autowired
    FarmDao farmDao;
//    @Autowired
//    FarmRepository farmRepository;

    @Override
    @Transactional
    public User save(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Authority authority = authorityRepository.findByName(AuthorityName.ROLE_OWNER);
        user.getAuthorities().add(authority);
        Farm farm = farmDao.save(Farm.builder().build());
        farm.getHaveUsers().add(user);
        user.setAffiliation(farm);
//        farmRepository.save(farm);

        return userDao.save(user);
    }

    @Override
    @Transactional
    public User saveFarmEmployee(User farmEmployee) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        farmEmployee.setPassword(encoder.encode(farmEmployee.getPassword()));
        farmEmployee.setEnabled(true);
        farmEmployee.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Authority authority = authorityRepository.findByName(AuthorityName.ROLE_EMPLOYEE);
        farmEmployee.getAuthorities().add(authority);

        return userDao.save(farmEmployee);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getUser(Pageable.unpaged()).getContent();
    }

    @Override
    public Page<User> getUserListPage(Integer page, Integer pageSize) {
        return userDao.getUser(PageRequest.of(page,pageSize));
    }

    @Override
    public Page<User> getFarmOwner(AuthorityName authoritiesName, Pageable pageable) {
        return userDao.getFarmOwner(authoritiesName,pageable);
    }

    @Override
    public Page<User> getFarmEmployee(AuthorityName authoritiesName, Long affiliation, Pageable pageable) {
        return userDao.getFarmEmployee(authoritiesName, affiliation, pageable);
    }

    public Page<User> getSearchFarmList(AuthorityName authoritiesName, String fullName, AuthorityName authoritiesName2, String username, Pageable page) {
        return userDao.getSearchFarmList(authoritiesName, fullName, authoritiesName2, username, page);
    }

    public Page<User> getSearchFarmEmployeeList(AuthorityName authoritiesName, Long affiliation, String fullName, AuthorityName authoritiesName2, Long affiliation2, String username, Pageable page) {
        return userDao.getSearchFarmEmployeeList(authoritiesName, affiliation, fullName, authoritiesName2, affiliation2, username, page);
    }
    @Transactional
    public Long deleteUserById(Long id) {
        return userDao.deleteUserById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }
}