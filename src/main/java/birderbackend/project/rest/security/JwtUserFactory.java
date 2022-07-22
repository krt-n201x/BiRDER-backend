package birderbackend.project.rest.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import birderbackend.project.rest.security.entity.Authority;
import birderbackend.project.rest.security.entity.JwtUser;
import birderbackend.project.rest.security.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
//                user.getAffiliation(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getFullName(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
