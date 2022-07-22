package birderbackend.project.rest.service;

import birderbackend.project.rest.dao.UserDao;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.security.entity.Authority;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.AuthorityRepository;
import birderbackend.project.rest.security.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//@ExtendWith(MockitoExtension.class)

class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    private User user;
    private Farm farm;

    @BeforeEach
    public void setup() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Authority authOwner = Authority.builder().name(AuthorityName.ROLE_OWNER).build();
        userRepository = Mockito.mock(UserRepository.class);
        userService = Mockito.mock(UserServiceImpl.class);
        user = User.builder()
                .id(5L)
                .username("sam")
                .password(encoder.encode("sam01"))
                .email("sam01@gmail.com")
                .address("111 M.2 A.Mueng P.Chiang Mai Thailand 50000")
                .phoneNumber("081-8000000")
                .fullName("Sam Samala")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        farm = Farm.builder()
                .id(1L)
                .build();
        user.setAffiliation(farm);
//        farm.getHaveUsers().add(user);
//        authorityRepository.save(authOwner);
        user.getAuthorities().add(authOwner);

    }


    @Test
    void saveTestCase() throws Exception {
        // Test Case 1
        given(userService.save(user)).willReturn(user);
        System.out.println(userRepository);
        System.out.println(userService);
        // when -  action or the behaviour that we are going test
        User savedUser = userService.save(user);
        System.out.println(savedUser);
        // then - verify the output
        assertThat(savedUser).isNotNull();

        // Test Case 2
        given(userService.save(user)).willReturn(null);
        User savedDuplicateUser = userService.save(user);
        assertThat(savedDuplicateUser).isNull();

        // Test Case 3
        user.setUsername("");
        given(userService.save(user)).willReturn(null);
        User savedMissingDataUser = userService.save(user);
        assertThat(savedMissingDataUser).isNull();

        // Test Case 4
        user.setUsername("");
        user.setPassword("");
        user.setEmail("");
        user.setAddress("");
        user.setPhoneNumber("");
        user.setFullName("");
        given(userService.save(user)).willReturn(null);
        User savedMissingAllDataUser = userService.save(user);
        assertThat(savedMissingAllDataUser).isNull();

    }



    @Test
    void saveFarmEmployeeTest() {
    }
}