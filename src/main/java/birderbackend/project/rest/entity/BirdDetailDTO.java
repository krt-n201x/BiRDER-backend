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
public class BirdDetailDTO {
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
}
