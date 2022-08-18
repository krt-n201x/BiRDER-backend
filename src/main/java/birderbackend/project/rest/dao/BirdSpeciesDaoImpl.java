package birderbackend.project.rest.dao;

import birderbackend.project.rest.entity.BirdSpecies;
import birderbackend.project.rest.repository.BirdSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public BirdSpecies saveBirdSpeciesInfo(BirdSpecies birdSpeciesInfo) {
        return birdSpeciesRepository.save(birdSpeciesInfo);
    }

    @Override
    public Optional<BirdSpecies> findById(Long id) {
        return birdSpeciesRepository.findById(id);
    }

    @Override
    public BirdSpecies getBirdSpecies(Long id) {
        return birdSpeciesRepository.findById(id).orElse(null);
    }

    @Override
    public List<BirdSpecies> getBirdSpeciesListWithoutPaging(Long affiliation_id) {
        return birdSpeciesRepository.findByAffiliation_Id(affiliation_id);
    }
}
