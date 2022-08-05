package birderbackend.project.rest.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Egg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    @Column(name = "EGGTYPE", length = 50)
    @NotNull
    private String eggType;

    @Column(name = "EGGSTATUS", length = 50)
    @NotNull
    private String eggStatus;

    @Column(name = "LAYDATE")
    @NotNull
    private Date layDate;

    @ManyToOne(fetch = FetchType.EAGER)
    BirdBreeding birdBreedingId;
}
