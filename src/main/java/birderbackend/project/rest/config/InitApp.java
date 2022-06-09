package birderbackend.project.rest.config;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.BirdStatus;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.BirdRepository;
import birderbackend.project.rest.repository.BirdStatusRepository;
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

    @Autowired
    BirdRepository birdRepository;

    @Autowired
    BirdStatusRepository birdStatusRepository;

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
        Farm farm1, farm2;
        farm1 = farmRepository.save(Farm.builder().build());
        user3.setAffiliation(farm1);
        user4.setAffiliation(farm1);
        farm1.getHaveUsers().add(user3);
        farm1.getHaveUsers().add(user4);

        farm2 = farmRepository.save(Farm.builder().build());
        user5.setAffiliation(farm2);
        user6.setAffiliation(farm2);
        user7.setAffiliation(farm2);
        farm2.getHaveUsers().add(user5);
        farm2.getHaveUsers().add(user6);
        farm2.getHaveUsers().add(user7);

        addBird();
        bird1.setAffiliation(farm1);
        farm1.getHaveBirds().add(bird1);
        bird2.setAffiliation(farm1);
        farm1.getHaveBirds().add(bird2);
        bird3.setAffiliation(farm1);
        farm1.getHaveBirds().add(bird3);

        bird4.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird4);
        bird5.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird5);

    }

    User user1, user2, user3, user4, user5, user6, user7;
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
                .address("1/1 M.1 A.Mueng P.Chiang Mai 50000")
                .phoneNumber("080-1111111")
                .fullName("Smith Smath")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user4 = User.builder()
                .username("ron")
                .password(encoder.encode("ron01"))
                .email("ron@gmail.com")
                .address("1/10 M.1 A.Mueng P.Chiang Mai 50000")
                .phoneNumber("080-1111110")
                .fullName("Ron Ran")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user5 = User.builder()
                .username("philip")
                .password(encoder.encode("philip01"))
                .email("philip@gmail.com")
                .address("1/12 M.12 A.Mueng P.Chiang Mai 50000")
                .phoneNumber("080-1211111")
                .fullName("Philip Phila")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user6 = User.builder()
                .username("henry")
                .password(encoder.encode("henry01"))
                .email("henry@gmail.com")
                .address("1/13 M.13 A.Mueng P.Chiang Mai 50000")
                .phoneNumber("080-1311110")
                .fullName("Henry Henra")
                .enabled(true)
                .lastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        user7 = User.builder()
                .username("potter")
                .password(encoder.encode("potter01"))
                .email("potter@gmail.com")
                .address("1/14 M.13 A.Mueng P.Chiang Mai 50000")
                .phoneNumber("080-1411110")
                .fullName("Potter Potty")
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
        user5.getAuthorities().add(authOwner);
        user6.getAuthorities().add(authEmployee);
        user7.getAuthorities().add(authEmployee);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);
    }

    Bird bird1, bird2, bird3, bird4, bird5;
    private void addBird() {

        BirdStatus status1, status2, status3, status4, status5, status6, status7, status8;
        status1 = BirdStatus.builder()
                .birdStatus("Sold")
                .build();
        status2 = BirdStatus.builder()
                .birdStatus("For sale")
                .build();
        status3 = BirdStatus.builder()
                .birdStatus("Available")
                .build();
        status4 = BirdStatus.builder()
                .birdStatus("Paired")
                .build();
        status5 = BirdStatus.builder()
                .birdStatus("Deceased")
                .build();
        status6 = BirdStatus.builder()
                .birdStatus("Exchanged")
                .build();
        status7 = BirdStatus.builder()
                .birdStatus("Lost")
                .build();
        status8 = BirdStatus.builder()
                .birdStatus("Donated")
                .build();

        birdStatusRepository.save(status1);
        birdStatusRepository.save(status2);
        birdStatusRepository.save(status3);
        birdStatusRepository.save(status4);
        birdStatusRepository.save(status5);
        birdStatusRepository.save(status6);
        birdStatusRepository.save(status7);
        birdStatusRepository.save(status8);



        bird1 = Bird.builder()
                .birdName("Mercury")
                .birdCode("#00100")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("A#010")
                .sexOfBird("M")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("Disease: Red eyes\n" +
                        "Symptoms: Got sore and red eye\n" +
                        "Treatment: Change cage\n" +
                        "Period of doing treatment: 5 days\n")
                .birdStatus(status4)
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird2 = Bird.builder()
                .birdName("Venus")
                .birdCode("#00200")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .birdSpecies("Cockatiel")
                .birdColor("Red and Yellow")
                .cageNumber("A#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180208/fre/kisspng-bird-watercolor-painting-parrot-cockatiel-parrot-5a7c72a75dba85.9314319315181052553839.jpg")
                .birdTreatmentRecord("Never got disease")
                .birdStatus(status4)
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird3 = Bird.builder()
                .birdName("Earth")
                .birdCode("#00300")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .birdSpecies("Cockatiel")
                .birdColor("Red and Green")
                .cageNumber("A#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180223/gpe/kisspng-yellow-collared-lovebird-parrot-budgerigar-black-c-parrot-5a90ab3a318e36.030014651519430458203.jpg")
                .birdTreatmentRecord("")
                .birdStatus(status3)
                .maleParentId(bird1)
                .femaleParentId(bird2)
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird4 = Bird.builder()
                .birdName("Mars")
                .birdCode("#00001")
                .dateOfBirth(Date.from(LocalDate.of(2022, 02, 02).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .birdSpecies("Cockatiel")
                .birdColor("Bright Red and Orange")
                .cageNumber("A##010")
                .sexOfBird("M")
                .birdImage("https://banner2.cleanpng.com/20180613/sw/kisspng-cockatiel-budgerigar-lovebird-parakeet-cockatiel-5b2111eb528f81.5829014615288939313382.jpg")
                .birdTreatmentRecord("")
                .birdStatus(status3)
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId(bird5)
//                .affiliation(1)
                .build();
        bird5 = Bird.builder()
                .birdName("Jupiter")
                .birdCode("#00002")
                .dateOfBirth(Date.from(LocalDate.of(2022, 02, 02).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .birdSpecies("Cockatiel")
                .birdColor("Bright Red and Yellow")
                .cageNumber("A##010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20171220/ruq/yellow-parrot-png-images-free-download-5a3aeb99a29e88.95921514151381084166618216.jpg")
                .birdTreatmentRecord("Never got disease")
                .birdStatus(status3)
//                .maleParentId()
//                .femaleParentId()
                .paringBirdId(bird4)
//                .affiliation(1)
                .build();

        bird4.setParingBirdId(bird5);
        birdRepository.save(bird1);
        birdRepository.save(bird2);
        birdRepository.save(bird3);
        birdRepository.save(bird4);
        birdRepository.save(bird5);
    }
}
