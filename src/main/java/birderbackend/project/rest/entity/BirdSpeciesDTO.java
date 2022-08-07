package birderbackend.project.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirdSpeciesDTO {
    FarmDTO affiliation;
    Long id;
    String speciesName;
    String familyName;
    String speciesColor;

}
