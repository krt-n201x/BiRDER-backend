package birderbackend.project.rest.config;

import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import birderbackend.project.rest.security.entity.Authority;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.AuthorityRepository;
import birderbackend.project.rest.security.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    FarmRepository farmRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        Farm farm1;
//        farm1 = farmRepository.save(Farm.builder().build());
//        addUser();
//            farm1.setAffiliation(user3);
//            user3.setFarm(farm1);
//            farm1.setAffiliation(user4);
//            user4.setFarm(farm1);

        addUser();
        Farm farm1;
        farm1 = farmRepository.save(Farm.builder().build());
        user3.setAffiliation(farm1);
        user4.setAffiliation(farm1);
        farm1.getHaveUsers().add(user3);
        farm1.getHaveUsers().add(user4);

    }

    User user1, user2, user3, user4;
    private void addUser() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Authority authAdmin = Authority.builder().name(AuthorityName.ROLE_ADMIN).build();
        Authority authOwner = Authority.builder().name(AuthorityName.ROLE_OWNER).build();
        Authority authEmployee = Authority.builder().name(AuthorityName.ROLE_EMPLOYEE).build();

        user1 = User.builder()
                .username("kitsada")
                .password(encoder.encode("kitsada01"))
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user2 = User.builder()
                .username("nilawan")
                .password(encoder.encode("nilawan01"))
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user3 = User.builder()
                .username("smith")
                .password(encoder.encode("smith01"))
                .email("smith@gmail.com")
                .address("1/1 M.1 A.Mueng P. Chiang Mai 50000")
                .phoneNumber("080-1111111")
                .fullName("Smith Smath")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user4 = User.builder()
                .username("ron")
                .password(encoder.encode("ron01"))
                .email("ron@gmail.com")
                .address("1/10 M.1 A.Mueng P. Chiang Mai 50000")
                .phoneNumber("080-1111110")
                .fullName("Ron Ran")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        authorityRepository.save(authAdmin);
        authorityRepository.save(authOwner);
        authorityRepository.save(authEmployee);

        user1.getAuthorities().add(authAdmin);
//        user1.getAuthorities().add(authOwner);
//        user1.getAuthorities().add(authEmployee);
        user2.getAuthorities().add(authAdmin);
//        user2.getAuthorities().add(authOwner);
//        user2.getAuthorities().add(authEmployee);
        user3.getAuthorities().add(authOwner);
        user4.getAuthorities().add(authEmployee);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }
}
