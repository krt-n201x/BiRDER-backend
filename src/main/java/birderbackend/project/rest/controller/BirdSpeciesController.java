package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.BirdSpecies;
import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.entity.Planner;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.service.*;
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

@RestController
public class BirdSpeciesController {
    @Autowired
    UserService userService;

    @Autowired
    BirdSpeciesService birdSpeciesService;

    @Autowired
    FarmService farmService;

    @Autowired
    BirdService birdService;

    @GetMapping("/viewBirdSpeciesList")
    public ResponseEntity<?> getBirdSpeciesList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page, @RequestParam(value = "affiliation", required = false) Long affiliation){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
        page = page == null ? 1 : page;
        Page<BirdSpecies> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = birdSpeciesService.getBirdSpecies(affiliation, PageRequest.of(page - 1, perPage));
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getBirdSpeciesDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);
    }

    @GetMapping("/searchBirdSpeciesList")
    public ResponseEntity<?> getSearchBirdSpeciesList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page
            , @RequestParam(value = "speciesName", required = false) String speciesName
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
        page = page == null ? 1 : page;
        Page<BirdSpecies> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = birdSpeciesService.getSearchSpeciesList(affiliation, speciesName, PageRequest.of(page - 1, perPage));

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        System.out.println(pageOutput);
        return new ResponseEntity<>(LabMapper.INSTANCE.getBirdSpeciesDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);

    }

    @PostMapping("/createBirdSpeciesDetail")
    public ResponseEntity<?> createBirdSpeciesDetail(@RequestBody BirdSpecies birdSpeciesInfo
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (       !birdSpeciesInfo.getSpeciesName().equals("") && !birdSpeciesInfo.getFamilyName().equals("")
                && !birdSpeciesInfo.getSpeciesColor().equals("")) {

            Optional<Farm> farm =  farmService.findById(affiliation);
            Farm farmEntity = farm.get();
            birdSpeciesInfo.setAffiliation(farmEntity);
            farmEntity.getHaveSpecies().add(birdSpeciesInfo);

            BirdSpecies output = birdSpeciesService.saveBirdSpeciesInfo(birdSpeciesInfo);
            return ResponseEntity.ok(LabMapper.INSTANCE.getBirdSpeciesDTO(output));

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create bird species, some data might not correct.");
        }
    }
}
