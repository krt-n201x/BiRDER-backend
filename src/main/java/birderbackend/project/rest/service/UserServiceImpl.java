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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    UserDao userDao;
    @Autowired
    FarmDao farmDao;
    @Autowired
    FarmRepository farmRepository;

    @Override
    @Transactional
    public User save(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Authority authority = authorityRepository.findByName(AuthorityName.ROLE_OWNER);
        user.getAuthorities().add(authority);
        Farm farm = farmRepository.save(Farm.builder().build());
        farm.getHaveUsers().add(user);
        user.setAffiliation(farm);
        farmRepository.save(farm);

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
}