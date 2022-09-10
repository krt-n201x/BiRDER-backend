package birderbackend.project.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirdBreedingDTO {
    FarmDTO affiliation;
    Long id;
    String breedingCageNumber;
    String breedingClutch;
    Date breedingDate;
    String breedingStatus;
    BirdDetailDTO haveMale;
    BirdDetailDTO haveFemale;
    List<EggDTO> haveEggs;
}
