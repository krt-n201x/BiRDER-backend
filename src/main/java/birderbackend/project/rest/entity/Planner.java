package birderbackend.project.rest.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Planner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    @Column(name = "TITLE", length = 50)
    @NotNull
    private String title;

    @Column(name = "DESCRIPTION", length = 1000)
    @NotNull
    private String description;

    @Column(name = "DATEOFPLAN")
    @NotNull
    private Date dateOfPlan;

    @Column(name = "TIMEOFPLAN")
    @NotNull
    private Time timeOfPlan;

    @Column(name = "PLANSTATUS", length = 50)
    @NotNull
    private String planStatus;

    @Column(name = "LABELTAG", length = 25)
    @NotNull
    private String labelTag;

    @ManyToOne(fetch = FetchType.EAGER)
    Bird birdId;

    @ManyToOne(fetch = FetchType.EAGER)
    Farm affiliation;

}
