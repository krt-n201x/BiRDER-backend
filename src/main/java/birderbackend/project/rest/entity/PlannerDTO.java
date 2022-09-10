package birderbackend.project.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlannerDTO {
    FarmDTO affiliation;
    Long id;
    String title;
    String description;
    Date dateOfPlan;
    Time timeOfPlan;
    String planStatus;
    String labelTag;
    BirdDetailDTO birdId;
}
