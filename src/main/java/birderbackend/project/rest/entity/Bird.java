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
public class Bird {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    @Column(name = "BIRDNAME", length = 25)
    @NotNull
    private String birdName;

    @Column(name = "BIRDCODE", length = 15)
    @NotNull
    private String birdCode;

    @Column(name = "DATEOFBIRTH")
    @NotNull
    private Date dateOfBirth;

    @Column(name = "BIRDCOLOR", length = 100)
    @NotNull
    private String birdColor;

    @Column(name = "CAGENUMBER", length = 25)
    @NotNull
    private String cageNumber;

    @Column(name = "SEXOFBIRD")
    @NotNull
    private String sexOfBird;

    @Column(name = "BIRDIMAGE", length = 255)
    @NotNull
    private String birdImage;

    @Column(name = "BIRDTREATMENTRECORD", length = 1000)
//    @NotNull
    private String birdTreatmentRecord;

//    @Column(name = "BIRDSPECIES", length = 50)
//    @NotNull
//    private String birdSpecies;

    @Column(name = "BIRDSTATUS", length = 50)
    @NotNull
    private String birdStatus;

    @OneToOne
    Bird maleParentId;

    @OneToOne
    Bird femaleParentId;

    @OneToOne
    Bird paringBirdId;

    @ManyToOne(fetch = FetchType.EAGER)
    Farm affiliation;

    @ManyToOne(fetch = FetchType.EAGER)
    BirdSpecies birdSpeciesId;

    @OneToMany (mappedBy = "birdId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<Planner> recordIn = new ArrayList<>();

    @OneToMany (mappedBy = "haveMale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<BirdBreeding> breedMe = new ArrayList<>();

    @OneToMany (mappedBy = "haveFemale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<BirdBreeding> breedFe = new ArrayList<>();

}
