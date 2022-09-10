package birderbackend.project.rest.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BirdSpecies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    @Column(name = "SPECIESNAME", length = 50)
    @NotNull
    private String speciesName;

    @Column(name = "FAMILYNAME", length = 50)
    @NotNull
    private String familyName;

    @Column(name = "SPECIESCOLOR", length = 50)
    @NotNull
    private String speciesColor;

    @ManyToOne(fetch = FetchType.EAGER)
    Farm affiliation;

    @OneToMany (mappedBy = "birdSpeciesId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<Bird> haveSpecies = new ArrayList<>();
}
