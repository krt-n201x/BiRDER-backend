package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.*;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.service.*;
import birderbackend.project.rest.util.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class BirdBreedingController {
    @Autowired
    BirdBreedingService birdBreedingService;

    @Autowired
    UserService userService;

    @Autowired
    FarmService farmService;

    @Autowired
    EggService eggService;

    @Autowired
    BirdService birdService;

    @GetMapping("/viewBirdBreedingList")
    public ResponseEntity<?> viewBirdBreedingList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page, @RequestParam(value = "affiliation", required = false) Long affiliation){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
        page = page == null ? 1 : page;
        Page<BirdBreeding> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = birdBreedingService.getBirdBreeding(affiliation, PageRequest.of(page - 1, perPage));
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getBirdBreedingDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);
    }

    @GetMapping("/searchBirdBreedingList")
    public ResponseEntity<?> getSearchBirdBreedingList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page
            , @RequestParam(value = "breedingCageNumber", required = false) String breedingCageNumber
            , @RequestParam(value = "maleCode", required = false) Long haveMale_id
            , @RequestParam(value = "femaleCode", required = false) Long haveFemale_id
            , @RequestParam(value = "breedingStatus", required = false) String breedingStatus
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
        page = page == null ? 1 : page;
        Page<BirdBreeding> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = birdBreedingService.getSearchBirdBreedingList(affiliation, breedingCageNumber, affiliation, haveMale_id, affiliation, haveFemale_id, affiliation, breedingStatus, PageRequest.of(page - 1, perPage));

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        System.out.println(pageOutput);
        return new ResponseEntity<>(LabMapper.INSTANCE.getBirdBreedingDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);

    }

    @PostMapping("/createBirdBreedingDetail")
    public ResponseEntity<?> createBirdBreedingDetail(@RequestBody BirdBreeding birdBreedingInfo
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (       !birdBreedingInfo.getBreedingCageNumber().equals("") && !birdBreedingInfo.getBreedingClutch().equals("")
                && birdBreedingInfo.getBreedingDate()!=null && !birdBreedingInfo.getBreedingStatus().equals("")
                && birdBreedingInfo.getHaveMale()!=null && birdBreedingInfo.getHaveFemale()!=null) {

            Optional<Bird> maleBird = birdService.findById(birdBreedingInfo.getHaveMale().getId());
            Optional<Bird> femaleBird = birdService.findById(birdBreedingInfo.getHaveFemale().getId());

            if( maleBird.isPresent() && femaleBird.isPresent()
                && maleBird.get().getAffiliation().getId().equals(affiliation)
                && femaleBird.get().getAffiliation().getId().equals(affiliation)
                && !maleBird.get().getSexOfBird().equals(femaleBird.get().getSexOfBird())
                && maleBird.get().getBirdStatus().equals("Available") && femaleBird.get().getBirdStatus().equals("Available")){

                if(birdBreedingInfo.getHaveEggs()!=null){
                    List<Egg> eggs = birdBreedingInfo.getHaveEggs();
                    for(int i=0; i<eggs.size(); i++){
                        if( eggs.get(i).getLayDate() == null || eggs.get(i).getEggType().equals("")
                            || eggs.get(i).getEggStatus().equals("")){
                            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird breeding, egg data might not correct.");
                        }
                        else {
                            eggs.get(i).setBirdBreedingId(birdBreedingInfo);
                        }
                    }
                }

                Optional<Farm> farm =  farmService.findById(affiliation);
                Farm farmEntity = farm.get();
                birdBreedingInfo.setAffiliation(farmEntity);
                farmEntity.getHaveBreedings().add(birdBreedingInfo);
                //db
                maleBird.get().setBirdStatus("Paired");
                femaleBird.get().setBirdStatus("Paired");
                maleBird.get().setParingBirdId(femaleBird.get());
                femaleBird.get().setParingBirdId(maleBird.get());
                //return
                birdBreedingInfo.getHaveMale().setBirdStatus("Paired");
                birdBreedingInfo.getHaveFemale().setBirdStatus("Paired");

                BirdBreeding output = birdBreedingService.saveBirdBreedingInfo(birdBreedingInfo);
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdBreedingDTO(output));
            }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird breeding, some data might not correct.");
            }

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird breeding, some data might not correct.");
        }
    }

    @GetMapping("/viewBirdBreedingDetail/{id}")
    public ResponseEntity<?> viewBirdBreedingDetail(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Optional<BirdBreeding> birdBreeding = birdBreedingService.findById(id);

        AtomicReference<ResponseEntity<BirdBreedingDTO>> output = new AtomicReference<>();
        birdBreeding.ifPresentOrElse(bb -> {
            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                Long affiliation = user.getAffiliation().getId();
                if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)
                        && bb.getAffiliation().getId().equals(affiliation)) {
                    output.set(ResponseEntity.ok(LabMapper.INSTANCE.getBirdBreedingDTO(bb)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.", id));
                }
            } else {
                output.set(ResponseEntity.ok(LabMapper.INSTANCE.getBirdBreedingDTO(bb)));
            }
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.", id));
        });
        return output.get();
    }

    @PostMapping("/updateBirdBreedingDetail/{id}")
    public ResponseEntity<?> updateBirdBreedingDetail(@RequestBody BirdBreeding birdBreedingInfo, @PathVariable Long id
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        BirdBreeding target = birdBreedingService.getBirdBreeding(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (       !birdBreedingInfo.getBreedingCageNumber().equals("") && !birdBreedingInfo.getBreedingClutch().equals("")
                && birdBreedingInfo.getBreedingDate()!=null && !birdBreedingInfo.getBreedingStatus().equals("")
                && birdBreedingInfo.getHaveMale()!=null && birdBreedingInfo.getHaveFemale()!=null) {

            Optional<Bird> maleBird = birdService.findById(birdBreedingInfo.getHaveMale().getId());
            Optional<Bird> femaleBird = birdService.findById(birdBreedingInfo.getHaveFemale().getId());

//            System.out.println(maleBird.isPresent());
//            System.out.println(femaleBird.isPresent());
//            System.out.println(maleBird.get().getAffiliation().getId().equals(affiliation));
//            System.out.println(femaleBird.get().getAffiliation().getId().equals(affiliation));
//            System.out.println(!maleBird.get().getSexOfBird().equals(femaleBird.get().getSexOfBird()));
//            System.out.println(maleBird.get().getBirdStatus().equals("Available"));
//            System.out.println(femaleBird.get().getBirdStatus().equals("Available"));

            if( maleBird.isPresent() && femaleBird.isPresent()
                    && maleBird.get().getAffiliation().getId().equals(affiliation)
                    && femaleBird.get().getAffiliation().getId().equals(affiliation)
                    && !maleBird.get().getSexOfBird().equals(femaleBird.get().getSexOfBird())){

                target.setBreedingCageNumber(birdBreedingInfo.getBreedingCageNumber());
                target.setBreedingClutch(birdBreedingInfo.getBreedingClutch());
                target.setBreedingDate(birdBreedingInfo.getBreedingDate());
                target.setBreedingStatus(birdBreedingInfo.getBreedingStatus());

                //change pair bird
                if(!target.getHaveMale().equals(maleBird.get())){
                    target.getHaveMale().setBirdStatus("Available");
                    target.setHaveMale(maleBird.get());
                }
                if(!target.getHaveFemale().equals(femaleBird.get())){
                    target.getHaveFemale().setBirdStatus("Available");
                    target.setHaveFemale(femaleBird.get());
                }

                if(target.getBreedingStatus().equals("Done")){
                    //db
                    maleBird.get().setBirdStatus("Available");
                    femaleBird.get().setBirdStatus("Available");
                    maleBird.get().setParingBirdId(null);
                    femaleBird.get().setParingBirdId(null);
                    //return
                    target.getHaveMale().setBirdStatus("Available");
                    target.getHaveFemale().setBirdStatus("Available");
                }
                else {
                    //db
                    maleBird.get().setBirdStatus("Paired");
                    femaleBird.get().setBirdStatus("Paired");
                    maleBird.get().setParingBirdId(femaleBird.get());
                    femaleBird.get().setParingBirdId(maleBird.get());
                    //return
                    target.getHaveMale().setBirdStatus("Paired");
                    target.getHaveFemale().setBirdStatus("Paired");
                }

                //delete egg
                eggService.deleteEggByBirdBreedingId(id);
                target.getHaveEggs().clear();
                if(birdBreedingInfo.getHaveEggs()!=null){
                    List<Egg> eggs = birdBreedingInfo.getHaveEggs();
                    for(int i=0; i<eggs.size(); i++){
                        if( eggs.get(i).getLayDate() == null || eggs.get(i).getEggType().equals("")
                                || eggs.get(i).getEggStatus().equals("")){
                            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird breeding, egg data might not correct.");
                        }
                        else {
                            target.getHaveEggs().add(eggs.get(i));
                            eggs.get(i).setBirdBreedingId(target);
                        }
                    }
                }

                BirdBreeding output = birdBreedingService.saveBirdBreedingInfo(target);
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdBreedingDTO(output));
            }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird breeding, some data might not correct.");
            }

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird breeding, some data might not correct.");
        }
    }

    @PostMapping("/deleteBirdBreeding/{id}")
    public ResponseEntity<?> deleteBirdBreeding(@PathVariable("id") Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
//        User target = userService.getUser(id);
        Optional<BirdBreeding> target = birdBreedingService.findById(id);

        if (user != null && target.isPresent()) {
            if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                //db
                target.get().getHaveMale().setBirdStatus("Available");
                target.get().getHaveFemale().setBirdStatus("Available");
                target.get().getHaveMale().setParingBirdId(null);
                target.get().getHaveFemale().setParingBirdId(null);
                birdBreedingService.deleteBirdBreedingById(id);
                return ResponseEntity.ok(LabMapper.INSTANCE.getBirdBreedingDTO(target.get()));
            }
            else if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
                Long affiliation = user.getAffiliation().getId();
                if(Objects.equals(affiliation, target.get().getAffiliation().getId())){
                    //db
                    target.get().getHaveMale().setBirdStatus("Available");
                    target.get().getHaveFemale().setBirdStatus("Available");
                    target.get().getHaveMale().setParingBirdId(null);
                    target.get().getHaveFemale().setParingBirdId(null);
                    birdBreedingService.deleteBirdBreedingById(id);
                    return ResponseEntity.ok(LabMapper.INSTANCE.getBirdBreedingDTO(target.get()));
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
        }
    }

    @GetMapping("/viewBirdBreedingPedigree/{id}")
    public ResponseEntity<?> viewBirdBreedingPedigree(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Optional<Bird> bird = birdService.findById(id);

        AtomicReference<ResponseEntity<PedigreeDTO>> output = new AtomicReference<>();
        bird.ifPresentOrElse(bp -> {
            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                Long affiliation = user.getAffiliation().getId();
                if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)
                        && bp.getAffiliation().getId().equals(affiliation)) {
                    output.set(ResponseEntity.ok(LabMapper.INSTANCE.getBirdPedigreeDTO(bp)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.", id));
                }
            } else {
                output.set(ResponseEntity.ok(LabMapper.INSTANCE.getBirdPedigreeDTO(bp)));
            }
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.", id));
        });
        return output.get();
    }


    @GetMapping("/getMaleOrFemaleBirdListForBreeding")
    public ResponseEntity<?> getMaleOrFemaleBirdListForBreeding(Long affiliation, String sex){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        List<Bird> pageOutput;
        pageOutput = birdService.getMaleOrFemaleBirdList(sex, affiliation);

        return ResponseEntity.ok(LabMapper.INSTANCE.getBirdDTO(pageOutput));
    }
}
