package birderbackend.project.rest.dao;

import java.util.List;

public interface EggDao {

    List<Long> deleteEggByBirdBreedingId(Long birdBreedingId);

}
