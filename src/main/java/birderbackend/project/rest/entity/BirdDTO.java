package birderbackend.project.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirdDTO {
    FarmDTO affiliation;
    Long id;
    String birdName;
    String birdCode;
    Date dateOfBirth;
    String birdColor;
    String cageNumber;
    String sexOfBird;
    String birdImage;
    String birdTreatmentRecord;
    String birdSpecies;
    String birdStatus;
    BirdDetailDTO maleParentId;
    BirdDetailDTO femaleParentId;
    BirdDetailDTO paringBirdId;
}
