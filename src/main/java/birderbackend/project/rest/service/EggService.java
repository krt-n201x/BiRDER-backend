package birderbackend.project.rest.service;

import java.util.List;

public interface EggService {

    List<Long> deleteEggByBirdBreedingId(Long birdBreedingId);
}
