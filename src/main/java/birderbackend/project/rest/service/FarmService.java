package birderbackend.project.rest.service;

import birderbackend.project.rest.entity.Farm;

import java.util.Optional;

public interface FarmService {

    Optional<Farm> findById(Long id);

    Long deleteFarmById(Long id);
}
