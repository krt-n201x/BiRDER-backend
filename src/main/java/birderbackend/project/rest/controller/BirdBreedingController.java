package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.Bird;
import birderbackend.project.rest.entity.BirdBreeding;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.service.BirdBreedingService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BirdBreedingController {
    @Autowired
    BirdBreedingService birdBreedingService;

    @Autowired
    UserService userService;

    @Autowired
    FarmService farmService;

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
}
