package birderbackend.project.rest.config;

import birderbackend.project.rest.entity.*;
import birderbackend.project.rest.repository.*;
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
import java.sql.Time;
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
    BirdBreedingRepository birdBreedingRepository;

    @Autowired
    EggRepository eggRepository;

    @Autowired
    BirdSpeciesRepository birdSpeciesRepository;

    @Autowired
    PlannerRepository plannerRepository;

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
        addBird();
        addBirdSpecies();
        addPlanner();
        addEggs();
        addBirdBreeding();


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

        species1.setAffiliation(farm1);
        farm1.getHaveSpecies().add(species1);

//        // Farm have breeding
        birdBreeding1.setAffiliation(farm2);
        farm2.getHaveBreedings().add(birdBreeding1);

        birdBreeding2.setAffiliation(farm1);
        farm1.getHaveBreedings().add(birdBreeding2);

//        // Bird have breeding
        birdBreeding1.setHaveMale(bird4);
        birdBreeding1.setHaveFemale(bird5);
        bird4.getBreedMe().add(birdBreeding1);
        bird5.getBreedFe().add(birdBreeding1);

        birdBreeding2.setHaveMale(bird1);
        birdBreeding2.setHaveFemale(bird2);
        bird1.getBreedMe().add(birdBreeding2);
        bird2.getBreedFe().add(birdBreeding2);


//        // Breeding have egg
        egg1.setBirdBreedingId(birdBreeding1);
        birdBreeding1.getHaveEggs().add(egg1);
        egg2.setBirdBreedingId(birdBreeding1);
        birdBreeding1.getHaveEggs().add(egg2);
        egg3.setBirdBreedingId(birdBreeding1);
        birdBreeding1.getHaveEggs().add(egg3);


//        addBird();
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
        bird6.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird6);
        bird7.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird7);
        bird8.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird8);
        bird9.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird9);
        bird10.setAffiliation(farm2);
        farm2.getHaveBirds().add(bird10);

//        addBirdSpecies();
        species1.setAffiliation(farm1);
        farm1.getHaveSpecies().add(species1);
        species3.setAffiliation(farm1);
        farm1.getHaveSpecies().add(species3);

        species2.setAffiliation(farm2);
        farm2.getHaveSpecies().add(species2);
//        species3.setAffiliation(farm2);
//        farm2.getHaveSpecies().add(species3);
        species4.setAffiliation(farm2);
        farm2.getHaveSpecies().add(species4);
        species5.setAffiliation(farm2);
        farm2.getHaveSpecies().add(species5);

        bird1.setBirdSpeciesId(species1);
        bird2.setBirdSpeciesId(species1);
        bird3.setBirdSpeciesId(species3);
        bird4.setBirdSpeciesId(species2);
        bird5.setBirdSpeciesId(species2);
        bird6.setBirdSpeciesId(species2);
        bird7.setBirdSpeciesId(species4);
        bird8.setBirdSpeciesId(species4);
        bird9.setBirdSpeciesId(species5);
        bird10.setBirdSpeciesId(species5);

        species1.getHaveSpecies().add(bird1);
        species1.getHaveSpecies().add(bird2);
        species3.getHaveSpecies().add(bird3);
        species2.getHaveSpecies().add(bird4);
        species2.getHaveSpecies().add(bird5);
        species2.getHaveSpecies().add(bird6);
        species4.getHaveSpecies().add(bird7);
        species4.getHaveSpecies().add(bird8);
        species5.getHaveSpecies().add(bird9);
        species5.getHaveSpecies().add(bird10);

        bird1.setAffiliation(farm1);
        farm1.getHaveBirds().add(bird1);
        bird2.setAffiliation(farm1);
        farm1.getHaveBirds().add(bird2);
        bird3.setAffiliation(farm1);
        farm1.getHaveBirds().add(bird3);

        planner1.setBirdId(bird1);
        bird1.getRecordIn().add(planner1);
        planner2.setBirdId(bird2);
        bird2.getRecordIn().add(planner2);

        planner3.setBirdId(bird3);
        bird3.getRecordIn().add(planner3);
        planner4.setBirdId(bird4);
        bird4.getRecordIn().add(planner4);
        planner5.setBirdId(bird5);
        bird5.getRecordIn().add(planner5);


        planner1.setAffiliation(farm1);
        farm1.getHavePlans().add(planner1);
        planner2.setAffiliation(farm1);
        farm1.getHavePlans().add(planner2);

        planner3.setAffiliation(farm2);
        farm2.getHavePlans().add(planner3);
        planner4.setAffiliation(farm2);
        farm2.getHavePlans().add(planner4);
        planner5.setAffiliation(farm2);
        farm2.getHavePlans().add(planner5);


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

    Bird bird1, bird2, bird3, bird4, bird5, bird6, bird7, bird8, bird9, bird10;
    private void addBird() {

//        BirdStatus status1, status2, status3, status4, status5, status6, status7, status8;
//        status1 = BirdStatus.builder()
//                .birdStatus("Sold")
//                .build();
//        status2 = BirdStatus.builder()
//                .birdStatus("For sale")
//                .build();
//        status3 = BirdStatus.builder()
//                .birdStatus("Available")
//                .build();
//        status4 = BirdStatus.builder()
//                .birdStatus("Paired")
//                .build();
//        status5 = BirdStatus.builder()
//                .birdStatus("Deceased")
//                .build();
//        status6 = BirdStatus.builder()
//                .birdStatus("Exchanged")
//                .build();
//        status7 = BirdStatus.builder()
//                .birdStatus("Lost")
//                .build();
//        status8 = BirdStatus.builder()
//                .birdStatus("Donated")
//                .build();
//        status8 = BirdStatus.builder()
//                .birdStatus("Unavailable")
//                .build();
//
//        birdStatusRepository.save(status1);
//        birdStatusRepository.save(status2);
//        birdStatusRepository.save(status3);
//        birdStatusRepository.save(status4);
//        birdStatusRepository.save(status5);
//        birdStatusRepository.save(status6);
//        birdStatusRepository.save(status7);
//        birdStatusRepository.save(status8);


        bird1 = Bird.builder()
                .birdName("Mercury")
                .birdCode("#00100")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("A#010")
                .sexOfBird("M")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("Disease: Red eyes\n" +
                        "Symptoms: Got sore and red eye\n" +
                        "Treatment: Change cage\n" +
                        "Period of doing treatment: 5 days\n")
                .birdStatus("Paired")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird2 = Bird.builder()
                .birdName("Venus")
                .birdCode("#00200")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Yellow")
                .cageNumber("A#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180208/fre/kisspng-bird-watercolor-painting-parrot-cockatiel-parrot-5a7c72a75dba85.9314319315181052553839.jpg")
                .birdTreatmentRecord("Never got disease")
                .birdStatus("Paired")
//                .maleParentId()
//                .femaleParentId()
                .paringBirdId(bird1)
//                .affiliation(1)
                .build();
        bird3 = Bird.builder()
                .birdName("Earth")
                .birdCode("#00300")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Green")
                .cageNumber("A#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180223/gpe/kisspng-yellow-collared-lovebird-parrot-budgerigar-black-c-parrot-5a90ab3a318e36.030014651519430458203.jpg")
                .birdTreatmentRecord("Nothing")
                .birdStatus("Available")
                .maleParentId(bird1)
                .femaleParentId(bird2)
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird4 = Bird.builder()
                .birdName("Mars")
                .birdCode("#00001")
                .dateOfBirth(Date.from(LocalDate.of(2022, 02, 02).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Bright Red and Orange")
                .cageNumber("A##010")
                .sexOfBird("M")
                .birdImage("https://banner2.cleanpng.com/20180613/sw/kisspng-cockatiel-budgerigar-lovebird-parakeet-cockatiel-5b2111eb528f81.5829014615288939313382.jpg")
                .birdTreatmentRecord("Nothing")
                .birdStatus("Paired")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId(bird5)
//                .affiliation(1)
                .build();
        bird5 = Bird.builder()
                .birdName("Jupiter")
                .birdCode("#00002")
                .dateOfBirth(Date.from(LocalDate.of(2022, 02, 02).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Bright Red and Yellow")
                .cageNumber("A##010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20171220/ruq/yellow-parrot-png-images-free-download-5a3aeb99a29e88.95921514151381084166618216.jpg")
                .birdTreatmentRecord("Never got disease")
                .birdStatus("Paired")
//                .maleParentId()
//                .femaleParentId()
                .paringBirdId(bird4)
//                .affiliation(1)
                .build();
        bird6 = Bird.builder()
                .birdName("Saturn")
                .birdCode("#00A00")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("AA#010")
                .sexOfBird("M")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("No record")
                .birdStatus("Available")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird7 = Bird.builder()
                .birdName("Uranus")
                .birdCode("#00B00")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("AA#010")
                .sexOfBird("M")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("No record")
                .birdStatus("Available")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird8 = Bird.builder()
                .birdName("Neptune")
                .birdCode("#00C00")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("AA#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("No record")
                .birdStatus("Available")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird9 = Bird.builder()
                .birdName("Pluto")
                .birdCode("#00D00")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("AA#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("No record")
                .birdStatus("Available")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();
        bird10 = Bird.builder()
                .birdName("SunMoon")
                .birdCode("#00E00")
                .dateOfBirth(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .birdSpecies("Cockatiel")
                .birdColor("Red and Orange")
                .cageNumber("AA#010")
                .sexOfBird("F")
                .birdImage("https://banner2.cleanpng.com/20180211/pcq/kisspng-bird-cockatiel-dog-cockatoo-rope-parrot-5a807f3e5a04f6.2532320115183706223687.jpg")
                .birdTreatmentRecord("No record")
                .birdStatus("Available")
//                .maleParentId()
//                .femaleParentId()
//                .paringBirdId("2")
//                .affiliation(1)
                .build();

        bird1.setParingBirdId(bird2);
        bird4.setParingBirdId(bird5);

        birdRepository.save(bird1);
        birdRepository.save(bird2);
        birdRepository.save(bird3);
        birdRepository.save(bird4);
        birdRepository.save(bird5);
        birdRepository.save(bird6);
        birdRepository.save(bird7);
        birdRepository.save(bird8);
        birdRepository.save(bird9);
        birdRepository.save(bird10);
    }

    BirdSpecies species1, species2, species3, species4, species5, species6, species7;
    private void addBirdSpecies() {
        species1 = BirdSpecies.builder()
                .speciesName("Forpus")
                .familyName("Psittacidae")
                .speciesColor("Pastel")
                .build();
        species2 = BirdSpecies.builder()
                .speciesName("Love Bird")
                .familyName("Psittacidae")
                .speciesColor("Red and Green")
                .build();
        species3 = BirdSpecies.builder()
                .speciesName("Conure")
                .familyName("Psittacidae")
                .speciesColor("Red and Yellow and Green")
                .build();
        species4 = BirdSpecies.builder()
                .speciesName("Parakeets")
                .familyName("Psittacidae")
                .speciesColor("Blue and Green")
                .build();
        species5 = BirdSpecies.builder()
                .speciesName("Cockatiel")
                .familyName("Psittacidae")
                .speciesColor("Grey and Yellow")
                .build();

        birdSpeciesRepository.save(species1);
        birdSpeciesRepository.save(species2);
        birdSpeciesRepository.save(species3);
        birdSpeciesRepository.save(species4);
        birdSpeciesRepository.save(species5);
    }

    Planner planner1, planner2, planner3, planner4, planner5;
    private void addPlanner() {
        long now = System.currentTimeMillis();
        Time sqlTime = new Time(now);

        planner1 = Planner.builder()
                .title("Check Health")
                .description("Check Health of the bird.")
                .dateOfPlan(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .timeOfPlan(sqlTime)
                .planStatus("In progress")
                .labelTag("Health")
                .build();
        planner2 = Planner.builder()
                .title("Check Food")
                .description("Check Food if bird eat.")
                .dateOfPlan(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .timeOfPlan(sqlTime)
                .planStatus("In progress")
                .labelTag("Food")
                .build();
        planner3 = Planner.builder()
                .title("Check Activity")
                .description("Check Activity that will train bird.")
                .dateOfPlan(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .timeOfPlan(sqlTime)
                .planStatus("In progress")
                .labelTag("Activity")
                .build();
        planner4 = Planner.builder()
                .title("Check Trade")
                .description("Check Trade of bird if ready to sell.")
                .dateOfPlan(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .timeOfPlan(sqlTime)
                .planStatus("In progress")
                .labelTag("Trade")
                .build();
        planner5 = Planner.builder()
                .title("Check Breed")
                .description("Check Breed if in breeding season.")
                .dateOfPlan(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .timeOfPlan(sqlTime)
                .planStatus("Done")
                .labelTag("Breed")
                .build();

        plannerRepository.save(planner1);
        plannerRepository.save(planner2);
        plannerRepository.save(planner3);
        plannerRepository.save(planner4);
        plannerRepository.save(planner5);
    }

    BirdBreeding birdBreeding1, birdBreeding2;
    private void addBirdBreeding() {
        birdBreeding1 = BirdBreeding.builder()
                .breedingCageNumber("Breeding#0405")
                .breedingClutch("3")
                .breedingDate(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .breedingStatus("In progress")
                .build();
        birdBreeding2 = BirdBreeding.builder()
                .breedingCageNumber("Breeding#0102")
                .breedingClutch("0")
                .breedingDate(Date.from(LocalDate.of(2022, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .breedingStatus("In progress")
                .build();

        birdBreedingRepository.save(birdBreeding1);
        birdBreedingRepository.save(birdBreeding2);
    }

    Egg egg1, egg2, egg3;
    private void addEggs() {
        egg1 = Egg.builder()
                .eggType("Fertilized")
                .eggStatus("In development")
                .layDate(Date.from(LocalDate.of(2022, 02, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        egg2 = Egg.builder()
                .eggType("Unknown")
                .eggStatus("Broken")
                .layDate(Date.from(LocalDate.of(2022, 02, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        egg3 = Egg.builder()
                .eggType("Fertilized")
                .eggStatus("Dead in shell")
                .layDate(Date.from(LocalDate.of(2022, 02, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        eggRepository.save(egg1);
        eggRepository.save(egg2);
        eggRepository.save(egg3);
    }

}
