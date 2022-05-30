package birderbackend.project.rest.dao;

import birderbackend.project.rest.security.entity.User;

public interface UserDao {
    User save(User user);
}
