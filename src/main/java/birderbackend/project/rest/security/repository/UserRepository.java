package birderbackend.project.rest.security.repository;

import birderbackend.project.rest.security.entity.AuthorityName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import birderbackend.project.rest.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Page<User> findByAuthorities_Name(AuthorityName authoritiesName, Pageable pageRequest);
    Page<User> findByAuthorities_NameAndAffiliation_Id(AuthorityName authoritiesName,Long affiliation, Pageable pageRequest);

//    Page<User> findByTitleContaining(String title, Pageable pageRequest);
//
//    Page<User> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageRequest);

    Page<User> findByAuthorities_NameAndFullNameContainingIgnoreCaseOrAuthorities_NameAndUsernameContainingIgnoreCase(AuthorityName authoritiesName, String fullName, AuthorityName authoritiesName2, String username, Pageable pageRequest);

    Page<User> findByAuthorities_NameAndAffiliation_IdAndFullNameContainingIgnoreCaseOrAuthorities_NameAndAffiliation_IdAndUsernameContainingIgnoreCase(AuthorityName authoritiesName, Long affiliation, String fullName, AuthorityName authoritiesName2, Long affiliation2, String username, Pageable pageRequest);
}
