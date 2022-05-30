package birderbackend.project.rest.security.entity;


import birderbackend.project.rest.entity.Farm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "USERNAME", length = 10, unique = true)
    @NotNull
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    private String password;

    @Column(name = "EMAIL", length = 50)
//    @NotNull
    private String email;

    @Column(name = "ADDRESS", length = 255)
//    @NotNull
    private String address;

    @Column(name = "PHONENUMBER", length = 12)
//    @NotNull
    private String phoneNumber;

    @Column(name = "FULLNAME", length = 50)
//    @NotNull
    private String fullName;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;

	@Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities = new ArrayList<>();

    @ManyToOne
    Farm affiliation;
}