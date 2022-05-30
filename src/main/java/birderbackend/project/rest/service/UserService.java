package birderbackend.project.rest.service;

import birderbackend.project.rest.security.entity.User;

public interface UserService {
    User save(User user);
    User saveFarmEmployee(User farmEmployee);
}
