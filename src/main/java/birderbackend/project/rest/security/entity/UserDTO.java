package birderbackend.project.rest.security.entity;

import birderbackend.project.rest.entity.FarmDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    FarmDTO affiliation;
    Long id;
    String username;
    String password;
    String email;
    String address;
    String phoneNumber;
    String fullName;
    Boolean enabled;
    Date lastPasswordResetDate;
}
