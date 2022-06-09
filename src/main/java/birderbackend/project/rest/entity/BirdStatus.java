package birderbackend.project.rest.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BirdStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    @Column(name = "BIRDSTATUS")
    @NotNull
    private String birdStatus;

//    @OneToOne
//    Bird bird;
}
