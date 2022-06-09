package birderbackend.project.rest.dao;

import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDao {
    User save(User user);

    User getUser(Long id);

    Page<User> getUser(Pageable pageRequest);

    Page<User> getFarmOwner(AuthorityName authoritiesName, Pageable page);

    Page<User> getFarmEmployee(AuthorityName authoritiesName, Long affiliation, Pageable page);

    Page<User> getSearchFarmList(AuthorityName authoritiesName, String fullName, AuthorityName authoritiesName2, String username, Pageable page);

    Page<User> getSearchFarmEmployeeList(AuthorityName authoritiesName, Long affiliation, String fullName, AuthorityName authoritiesName2, Long affiliation2, String username, Pageable page);

    Long deleteUserById(Long id);
}
