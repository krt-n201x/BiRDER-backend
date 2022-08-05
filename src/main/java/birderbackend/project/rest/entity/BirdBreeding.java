package birderbackend.project.rest.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BirdBreeding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    @Column(name = "BREEDINGCAGENUMBER", length = 25)
    @NotNull
    private String breedingCageNumber;

    @Column(name = "BREEDINGCLUTCH", length = 25)
    @NotNull
    private String breedingClutch;

    @Column(name = "BREEDINGDATE")
    @NotNull
    private Date breedingDate;

    @Column(name = "BREEDINGSTATUS", length = 50)
    @NotNull
    private String breedingStatus;

//    @OneToOne
//    Bird maleParentId;
//
//    @OneToOne
//    Bird femaleParentId;

    @ManyToOne(fetch = FetchType.EAGER)
    Farm affiliation;

    @OneToMany (mappedBy = "birdBreedingId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<Egg> haveEggs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    Bird haveMale;

    @ManyToOne(fetch = FetchType.EAGER)
    Bird haveFemale;
}
