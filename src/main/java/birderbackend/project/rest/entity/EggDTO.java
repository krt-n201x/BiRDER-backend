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
public class EggDTO {
    Long id;
    String eggType;
    String eggStatus;
    Date layDate;

}
