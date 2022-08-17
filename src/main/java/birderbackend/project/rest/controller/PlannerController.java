package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.*;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.service.BirdService;
import birderbackend.project.rest.service.FarmService;
import birderbackend.project.rest.service.PlannerService;
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
public class PlannerController {

    @Autowired
    UserService userService;

    @Autowired
    PlannerService plannerService;

    @Autowired
    FarmService farmService;

    @Autowired
    BirdService birdService;

    @GetMapping("/viewPlannerList")
    public ResponseEntity<?> getPlannerList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page, @RequestParam(value = "affiliation", required = false) Long affiliation){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
        page = page == null ? 1 : page;
        Page<Planner> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = plannerService.getPlanner(affiliation, PageRequest.of(page - 1, perPage));
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getPlannerDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);
    }

    @GetMapping("/searchPlannerList")
    public ResponseEntity<?> getSearchPlannerList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page
            , @RequestParam(value = "planStatus", required = false) String planStatus
            , @RequestParam(value = "labelTag", required = false) String labelTag
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        perPage = perPage == null ? 6 : perPage;
        page = page == null ? 1 : page;
        Page<Planner> pageOutput;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = plannerService.getSearchPlannerList(affiliation, planStatus, affiliation, labelTag, PageRequest.of(page - 1, perPage));

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        System.out.println(pageOutput);
        return new ResponseEntity<>(LabMapper.INSTANCE.getPlannerDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);

    }

    @PostMapping("/createPlannerDetail")
    public ResponseEntity<?> createPlannerDetail(@RequestBody Planner plannerInfo
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (       !plannerInfo.getTitle().equals("") && !plannerInfo.getDescription().equals("")
                && plannerInfo.getDateOfPlan() != null && plannerInfo.getTimeOfPlan()!= null
                && !plannerInfo.getPlanStatus().equals("") && !plannerInfo.getLabelTag().equals("") ) {

            if(plannerInfo.getBirdId() != null){
                if(birdService.getBird(plannerInfo.getBirdId().getId()) == null){
                    throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create planner, some data might not correct.");
                }
            }

            Optional<Farm> farm =  farmService.findById(affiliation);
            Farm farmEntity = farm.get();
            plannerInfo.setAffiliation(farmEntity);
            farmEntity.getHavePlans().add(plannerInfo);

            Planner output = plannerService.savePlannerInfo(plannerInfo);
            return ResponseEntity.ok(LabMapper.INSTANCE.getPlannerDTO(output));

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create planner, some data might not correct.");
        }
    }

    @GetMapping("/viewPlannerDetail/{id}")
    public ResponseEntity<?> viewPlannerDetail(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        Optional<Planner> planner = plannerService.findById(id);

        AtomicReference<ResponseEntity<PlannerDTO>> output = new AtomicReference<>();
        planner.ifPresentOrElse(pn -> {
            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                Long affiliation = user.getAffiliation().getId();
                if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)
                        && pn.getAffiliation().getId().equals(affiliation)) {
                    output.set(ResponseEntity.ok(LabMapper.INSTANCE.getPlannerDTO(pn)));
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.", id));
                }
            } else {
                output.set(ResponseEntity.ok(LabMapper.INSTANCE.getPlannerDTO(pn)));
            }
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.", id));
        });
        return output.get();
    }

    @PostMapping("/updatePlannerDetail/{id}")
    public ResponseEntity<?> updatePlannerDetail(@RequestBody Planner plannerInfo, @PathVariable Long id
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Planner target = plannerService.getPlanner(id);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findByUsername(auth.getName());
//
//        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
//            affiliation = user.getAffiliation().getId();
//        }

        if (       !plannerInfo.getTitle().equals("") && !plannerInfo.getDescription().equals("")
                && plannerInfo.getDateOfPlan() != null && plannerInfo.getTimeOfPlan()!= null
                && !plannerInfo.getPlanStatus().equals("") && !plannerInfo.getLabelTag().equals("") ) {

            if(plannerInfo.getBirdId() != null){
                if(birdService.getBird(plannerInfo.getBirdId().getId()) == null){
                    throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create planner, some data might not correct.");
                }
                else {
                    target.setBirdId(plannerInfo.getBirdId());
                }
            }

            target.setTitle(plannerInfo.getTitle());
            target.setDescription(plannerInfo.getDescription());
            target.setDateOfPlan(plannerInfo.getDateOfPlan());
            target.setTimeOfPlan(plannerInfo.getTimeOfPlan());
            target.setPlanStatus(plannerInfo.getPlanStatus());
            target.setLabelTag(plannerInfo.getLabelTag());

//            Optional<Farm> farm =  farmService.findById(affiliation);

            Planner output = plannerService.savePlannerInfo(target);
            return ResponseEntity.ok(LabMapper.INSTANCE.getPlannerDTO(output));

        }else {
//            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Could not create planner, some data might not correct.");
        }
    }

    @PostMapping("/deletePlanner/{id}")
    public ResponseEntity<?> deletePlanner(@PathVariable("id") Long id) {

        Planner target = plannerService.getPlanner(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Long affiliation = null;

        if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
            affiliation = user.getAffiliation().getId();
        }

        if (target != null) {
            if (!user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                if(target.getAffiliation().getId().equals(affiliation)){
                    plannerService.deletePlannerById(id);
                    return ResponseEntity.ok(LabMapper.INSTANCE.getPlannerDTO(target));
                }else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
                }
            }else {
                plannerService.deletePlannerById(id);
                return ResponseEntity.ok(LabMapper.INSTANCE.getPlannerDTO(target));
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("The given id %n is not found.",id));
        }
    }
}
