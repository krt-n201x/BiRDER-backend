package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.BirdSpecies;
import birderbackend.project.rest.repository.BirdSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BirdSpeciesDaoImpl implements BirdSpeciesDao{

    @Autowired
    BirdSpeciesRepository birdSpeciesRepository;

    @Override
    public Page<BirdSpecies> getBirdSpecies(Long affiliation, Pageable pageRequest) {
        return birdSpeciesRepository.findByAffiliation_Id(affiliation, pageRequest);
    }

    @Override
    public Page<BirdSpecies> getSearchSpeciesList(Long affiliation_id, String speciesName, Pageable pageable) {
        return birdSpeciesRepository.findByAffiliation_IdAndSpeciesNameContainingIgnoreCase(affiliation_id, speciesName, pageable);
    }
}
