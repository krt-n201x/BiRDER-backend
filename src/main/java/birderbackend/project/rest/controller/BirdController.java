package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.BirdDTO;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.service.BirdService;
import birderbackend.project.rest.service.FarmService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class BirdController {

    @Autowired
    BirdService birdService;

    @Autowired
    UserService userService;

    @Autowired
    FarmService farmService;


    @GetMapping("/viewBirdList")
    public ResponseEntity<?> getBirdList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page, @RequestParam(value = "affiliation", required = false) Long affiliation){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
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
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
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
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (       !birdInfo.getBirdName().equals("")
                && !birdInfo.getBirdCode().equals("") && !birdInfo.getDateOfBirth().equals("") && !birdInfo.getBirdColor().equals("")
                && !birdInfo.getCageNumber().equals("") && !birdInfo.getSexOfBird().equals("") && !birdInfo.getBirdImage().equals("")
                && !birdInfo.getBirdSpecies().equals("") && !birdInfo.getBirdStatus().equals("")) {
            if(birdService.getSearchByBirdNameBirdCode(
                    affiliation, birdInfo.getBirdName(), affiliation, birdInfo.getBirdCode()) == null){
                if(user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                    birdInfo.setParingBirdId(null);
                }

                Optional<Farm> farm =  farmService.findById(affiliation);
                Farm farmEntity = farm.get();
                birdInfo.setAffiliation(farmEntity);
                farmEntity.getHaveBirds().add(birdInfo);

                Bird output = birdService.saveBirdInfo(birdInfo);
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(output));
            }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird, some data might not correct.");
            }

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird, some data might not correct.");
        }
    }

    @GetMapping("/viewBirdDetail/{id}")
    public ResponseEntity<?> viewBirdDetail(@PathVariable Long id){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Optional<Bird> bird =  birdService.findById(id);

        AtomicReference<ResponseEntity<BirdDTO>> output = new AtomicReference<>();
        bird.ifPresentOrElse(b -> {
            Long affiliation = user.getAffiliation().getId();
                if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)
                        && b.getAffiliation().getId().equals(affiliation)){
                    output.set(ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(b)));
                }else{
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
            }
        },() ->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
        });
        return output.get();

//        if(bird.isPresent()){
//            Bird birdEntity = bird.get();
//            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
//                Long affiliation = user.getAffiliation().getId();
//
//                if(birdEntity.getAffiliation().getId().equals(affiliation)){
//                    return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(birdEntity));
//                }else{
////                    return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
//                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found.");
//
//                }
//            }else{
//                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(birdEntity));
//            }
//
//        }else{
////            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given %n is not found.",id));
//        }
    }

    @PostMapping("/updateBirdDetail/{id}")
    public ResponseEntity<?> updateBirdDetail(@RequestBody Bird birdInfo, @PathVariable Long id
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Bird target = birdService.getBird(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (       !birdInfo.getBirdName().equals("")
                && !birdInfo.getBirdCode().equals("") && !birdInfo.getDateOfBirth().equals("") && !birdInfo.getBirdColor().equals("")
                && !birdInfo.getCageNumber().equals("") && !birdInfo.getSexOfBird().equals("") && !birdInfo.getBirdImage().equals("")
                && !birdInfo.getBirdSpecies().equals("") && !birdInfo.getBirdStatus().equals("")) {


            if((birdService.getSearchByBirdName(affiliation, birdInfo.getBirdName())== null || // new name not duplicate other birds in db
                    birdService.getSearchByBirdName(affiliation, birdInfo.getBirdName()).getId().equals(target.getId()))&&
               (birdService.getSearchByBirdCode(affiliation, birdInfo.getBirdCode())== null || // new code not duplicate other birds in db
                       birdService.getSearchByBirdCode(affiliation, birdInfo.getBirdCode()).getId().equals(target.getId()))){
                target.setBirdName(birdInfo.getBirdName());
                target.setBirdCode(birdInfo.getBirdCode());
                target.setDateOfBirth(birdInfo.getDateOfBirth());
                target.setBirdColor(birdInfo.getBirdColor());
                target.setCageNumber(birdInfo.getCageNumber());
                target.setSexOfBird(birdInfo.getSexOfBird());
                target.setBirdImage(birdInfo.getBirdImage());
                target.setBirdTreatmentRecord(birdInfo.getBirdTreatmentRecord());
                target.setBirdSpecies(birdInfo.getBirdSpecies());
                target.setBirdStatus(birdInfo.getBirdStatus());
                target.setMaleParentId(birdInfo.getMaleParentId());
                target.setFemaleParentId(birdInfo.getFemaleParentId());
                if(!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                    target.setParingBirdId(birdInfo.getParingBirdId());
                }

                Bird output = birdService.saveBirdInfo(target);
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(output));
            }
            else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not update bird, some data might not correct.");
            }

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not update bird, some data might not correct.");
        }
    }

    @PostMapping("/deleteBird/{id}")
    public ResponseEntity<?> deleteBird(@PathVariable("id") Long id) {

        Bird target = birdService.getBird(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Long affiliation = null;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (target != null) {
            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                if(target.getAffiliation().getId().equals(affiliation)){
                    target.setBirdStatus("Unavailable");
//                    target.setMaleParentId(null);
//                    target.setFemaleParentId(null);
//                    target.setParingBirdId(null);
                    Bird output = birdService.saveBirdInfo(target);
//                    birdService.deleteBirdById(output.getId());
                    return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(output));
                }else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
                }
            }else {
                target.setBirdStatus("Unavailable");
//                target.setMaleParentId(null);
//                target.setFemaleParentId(null);
//                target.setParingBirdId(null);
                Bird output = birdService.saveBirdInfo(target);
//                birdService.deleteBirdById(output.getId());
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(output));
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
        }
    }
}
