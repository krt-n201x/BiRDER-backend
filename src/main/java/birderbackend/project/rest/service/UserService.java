package birderbackend.project.rest.service;

import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User save(User user);
    User saveFarmEmployee(User farmEmployee);

    User getUser(Long id);

    List<User> getAllUser();
    Page<User> getUserListPage(Integer page, Integer pageSize);

    Page<User> getFarmOwner(AuthorityName authoritiesName, Pageable pageable);

    Page<User> getFarmEmployee(AuthorityName authoritiesName, Long affiliation, Pageable pageable);

    Page<User> getSearchFarmList(AuthorityName authoritiesName, String fullName, AuthorityName authoritiesName2, String username, Pageable page);

    Page<User> getSearchFarmEmployeeList(AuthorityName authoritiesName, Long affiliation, String fullName, AuthorityName authoritiesName2, Long affiliation2, String username, Pageable page);

    Long deleteUserById(Long id);

}
