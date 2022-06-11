package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.BirdRepository;
import birderbackend.project.rest.repository.FarmRepository;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.UserRepository;
import birderbackend.project.rest.service.BirdService;
import birderbackend.project.rest.service.UserService;
import birderbackend.project.rest.util.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class BirdController {

    @Autowired
    BirdService birdService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BirdRepository birdRepository;

    @Autowired
    FarmRepository farmRepository;


    @GetMapping("/viewBirdList")
    public ResponseEntity<?> getBirdList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page, @RequestParam(value = "affiliation", required = false) Long affiliation){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<Bird> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = birdService.getBird(affiliation, PageRequest.of(page - 1, perPage));
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getBirdDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);
    }

    @GetMapping("/searchBirdList")
    public ResponseEntity<?> getSearchBirdList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page
            , @RequestParam(value = "birdName", required = false) String birdName
            , @RequestParam(value = "birdCode", required = false) String birdCode
            , @RequestParam(value = "birdSpecies", required = false) String birdSpecies
            , @RequestParam(value = "birdStatus", required = false) String birdStatus
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<Bird> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = birdService.getSearchBirdList(affiliation, birdName, affiliation, birdCode, affiliation, birdSpecies, affiliation, birdStatus, PageRequest.of(page - 1, perPage));

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getBirdDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);

    }

    @PostMapping("/createBirdDetail")
    public ResponseEntity<?> createBirdDetail(@RequestBody Bird birdInfo
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (birdRepository.findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCase(
                affiliation, birdInfo.getBirdName(), affiliation, birdInfo.getBirdCode()) == null && !birdInfo.getBirdName().equals("")
                && !birdInfo.getBirdCode().equals("") && !birdInfo.getDateOfBirth().equals("") && !birdInfo.getBirdColor().equals("")
                && !birdInfo.getCageNumber().equals("") && !birdInfo.getSexOfBird().equals("") && !birdInfo.getBirdImage().equals("")
                && !birdInfo.getBirdSpecies().equals("") && !birdInfo.getBirdStatus().equals("")) {

            if(user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                birdInfo.setParingBirdId(null);
            }

            Optional<Farm> farm =  farmRepository.findById(affiliation);
            Farm farmEntity = farm.get();
            birdInfo.setAffiliation(farmEntity);
            farmEntity.getHaveBirds().add(birdInfo);

            Bird output = birdService.saveBirdInfo(birdInfo);
            return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(output));

        }else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/viewBirdDetail/{id}")
    public ResponseEntity<?> viewBirdDetail(@PathVariable Long id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        Optional<Bird> bird =  birdRepository.findById(id);

        if(bird.isPresent()){
            Bird birdEntity = bird.get();
            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                Long affiliation = user.getAffiliation().getId();

                if(birdEntity.getAffiliation().getId().equals(affiliation)){
                    return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(birdEntity));
                }else{
                    return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
                }
            }else{
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(birdEntity));
            }

        }else{
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/updateBirdDetail/{id}")
    public ResponseEntity<?> updateBirdDetail(@RequestBody Bird birdInfo, @PathVariable Long id
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Bird target = birdService.getBird(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (birdRepository.findByAffiliation_IdAndBirdNameContainingIgnoreCaseOrAffiliation_IdAndBirdCodeContainingIgnoreCase(
                affiliation, birdInfo.getBirdName(), affiliation, birdInfo.getBirdCode()) == null && !birdInfo.getBirdName().equals("")
                && !birdInfo.getBirdCode().equals("") && !birdInfo.getDateOfBirth().equals("") && !birdInfo.getBirdColor().equals("")
                && !birdInfo.getCageNumber().equals("") && !birdInfo.getSexOfBird().equals("") && !birdInfo.getBirdImage().equals("")
                && !birdInfo.getBirdSpecies().equals("") && !birdInfo.getBirdStatus().equals("")) {


            target.setBirdName(birdInfo.getBirdName());
            target.setBirdCode(birdInfo.getBirdCode());
            target.setDateOfBirth(birdInfo.getDateOfBirth());
            target.setBirdColor(birdInfo.getBirdColor());
            target.setCageNumber(birdInfo.getCageNumber());
            target.setSexOfBird(birdInfo.getSexOfBird());
            target.setBirdImage(birdInfo.getBirdImage());
            target.setBirdSpecies(birdInfo.getBirdSpecies());
            target.setBirdStatus(birdInfo.getBirdStatus());
            target.setMaleParentId(birdInfo.getMaleParentId());
            target.setFemaleParentId(birdInfo.getFemaleParentId());
            if(!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                target.setParingBirdId(birdInfo.getParingBirdId());
            }

            Bird output = birdService.saveBirdInfo(target);
            return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(output));

        }else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
        }
    }
}
