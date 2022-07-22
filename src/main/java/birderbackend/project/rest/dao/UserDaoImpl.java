package birderbackend.project.rest.dao;

import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getUser(Pageable pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    @Override
    public Page<User> getFarmOwner(AuthorityName authoritiesName, Pageable page) {
        return userRepository.findByAuthorities_Name(authoritiesName,page);
    }

    @Override
    public Page<User> getFarmEmployee(AuthorityName authoritiesName, Long affiliation, Pageable page) {
        return userRepository.findByAuthorities_NameAndAffiliation_Id(authoritiesName, affiliation, page);
    }

    public Page<User> getSearchFarmList(AuthorityName authoritiesName, String fullName, AuthorityName authoritiesName2, String username, Pageable page) {
        return userRepository.findByAuthorities_NameAndFullNameContainingIgnoreCaseOrAuthorities_NameAndUsernameContainingIgnoreCase(authoritiesName, fullName, authoritiesName2, username, page);
    }

    public Page<User> getSearchFarmEmployeeList(AuthorityName authoritiesName, Long affiliation, String fullName, AuthorityName authoritiesName2, Long affiliation2, String username, Pageable page) {
        return userRepository.findByAuthorities_NameAndAffiliation_IdAndFullNameContainingIgnoreCaseOrAuthorities_NameAndAffiliation_IdAndUsernameContainingIgnoreCase(authoritiesName, affiliation, fullName, authoritiesName2, affiliation2, username, page);
    }

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

//    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long deleteUserById(Long id) {

        return userRepository.deleteUserById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


}
