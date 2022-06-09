package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.FarmRepository;
import birderbackend.project.rest.security.JwtTokenUtil;
import birderbackend.project.rest.security.entity.AuthorityName;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.UserRepository;
import birderbackend.project.rest.service.FarmService;
import birderbackend.project.rest.service.UserService;
import birderbackend.project.rest.util.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@RestController
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    FarmRepository farmRepository;

    @Autowired
    FarmService farmService;

    @PostMapping("/registers")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) == null && !user.getUsername().equals("")
                && !user.getPassword().equals("") && !user.getEmail().equals("") && !user.getAddress().equals("")
                && !user.getPhoneNumber().equals("") && !user.getFullName().equals("")) {
            User output = userService.save(user);
            return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(output));
        }else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/registers_farm_employee")
    public ResponseEntity<?> addFarmEmployee(HttpServletRequest request, @RequestBody User farmEmployee) {
        if (userRepository.findByUsername(farmEmployee.getUsername()) == null && !farmEmployee.getUsername().equals("")
                && !farmEmployee.getPassword().equals("") && !farmEmployee.getEmail().equals("") && !farmEmployee.getAddress().equals("")
                && !farmEmployee.getPhoneNumber().equals("") && !farmEmployee.getFullName().equals("")) {
            String authToken = request.getHeader(this.tokenHeader);
            if (authToken != null && authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
            }
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            User farmOwner = userRepository.findByUsername(username);
            Farm affiliation = farmOwner.getAffiliation();
//            System.out.println(affiliation.getId());
            farmEmployee.setAffiliation(affiliation);
            affiliation.getHaveUsers().add(farmEmployee);
            User output = userService.saveFarmEmployee(farmEmployee);
            return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(output));
//            return null;
        }else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/viewProfileDetail/{id}")
    public ResponseEntity<?> viewProfileDetail(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        User target = userService.getUser(id);

        if (user != null && target != null) {
            if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                if(!target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN) || user.equals(target)){
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                }
            }
            else if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
                if(user.equals(target)){
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }
                else if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                    Farm ownerAffiliation = user.getAffiliation();
                    Farm employeeAffiliation = target.getAffiliation();
                    if (ownerAffiliation.equals(employeeAffiliation)) {
                        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                    }
                    else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                    }
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                }
            }
            else if(user.equals(target)){
                return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }


    @PostMapping("/updateProfileDetail/{id}")
    public ResponseEntity<?> updateProfileDetail(@PathVariable("id") Long id, @RequestBody User userInfo
            , @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        User target = userService.getUser(id);

        boolean editAdmin = false;

        if (user != null && target != null) {
            if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN) && user.equals(target)) {
                    editAdmin = true;
                }
                else if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN) && !user.equals(target)) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                }
                else {
                    editAdmin = false;
                }
            }
            else if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
                if(user.equals(target)){
                    editAdmin = false;
                }
                else if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                    Farm ownerAffiliation = user.getAffiliation();
                    Farm employeeAffiliation = target.getAffiliation();
                    if (ownerAffiliation.equals(employeeAffiliation)) {
                        editAdmin = false;
                    }
                    else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                    }
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                }
            }
//            else if(user.equals(target)){
//                editAdmin = false;
//            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }

        if(editAdmin){
            target.setUsername(userInfo.getUsername());
        }
        else {
            target.setUsername(userInfo.getUsername());
            target.setEmail(userInfo.getEmail());
            target.setAddress(userInfo.getAddress());
            target.setPhoneNumber(userInfo.getPhoneNumber());
            target.setFullName(userInfo.getFullName());
        }
        if(!userInfo.getPassword().equals("")){
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            boolean isPasswordMatches = bcrypt.matches(
                    userInfo.getPassword(),
                    target.getPassword()
            );
            if (isPasswordMatches) { // correct password

                if (!newPassword.equals("") && !confirmPassword.equals("") && newPassword.equals(confirmPassword)) {
                    PasswordEncoder encoder = new BCryptPasswordEncoder();
                    target.setPassword(encoder.encode(newPassword));
                    userRepository.save(target);
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }
                else { // Wrong Password
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password not correct");
                }

            } else { // Wrong Password
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password not correct");
            }
        }else{
            userRepository.save(target);
            return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
        }
    }

    @GetMapping("/viewFarmList")
    public ResponseEntity<?> getFarmList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page) {
        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<User> pageOutput;
        pageOutput = userService.getFarmOwner(AuthorityName.ROLE_OWNER, PageRequest.of(page - 1, perPage));
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getUserDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);
    }

    @GetMapping("/viewFarmEmployeeList")
    public ResponseEntity<?> getFarmEmployeeList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page, @RequestParam(value = "affiliation", required = false) Long affiliation){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<User> pageOutput;

        if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = userService.getFarmEmployee(AuthorityName.ROLE_EMPLOYEE, affiliation, PageRequest.of(page - 1, perPage));
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getUserDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);
    }

//    @GetMapping("/viewFarmList")
//    ResponseEntity<?> getFarmList(){
//        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(userService.getAllUser()));
//    }

    @GetMapping("/searchFarmList")
    public ResponseEntity<?> getSearchFarmList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page
            , @RequestParam(value = "fullName", required = false) String fullName
            , @RequestParam(value = "username", required = false) String username) {

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<User> pageOutput;
//        if (fullName == null) {
//            pageOutput = userService.getSearch(perPage, page);
//        } else {
        pageOutput = userService.getSearchFarmList(AuthorityName.ROLE_OWNER, fullName, AuthorityName.ROLE_OWNER, username, PageRequest.of(page - 1, perPage));
//        }
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getUserDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);

    }

    @GetMapping("/searchFarmEmployeeList")
    public ResponseEntity<?> getSearchFarmEmployeeList(@RequestParam(value = "_limit", required = false) Integer perPage
            , @RequestParam(value = "_page", required = false) Integer page
            , @RequestParam(value = "fullName", required = false) String fullName
            , @RequestParam(value = "username", required = false) String username
            , @RequestParam(value = "affiliation", required = false) Long affiliation) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());

        perPage = perPage == null ? 3 : perPage;
        page = page == null ? 1 : page;
        Page<User> pageOutput;

        if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
            affiliation = user.getAffiliation().getId();
        }

        pageOutput = userService.getSearchFarmEmployeeList(AuthorityName.ROLE_EMPLOYEE, affiliation, fullName, AuthorityName.ROLE_EMPLOYEE, affiliation, username, PageRequest.of(page - 1, perPage));

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return new ResponseEntity<>(LabMapper.INSTANCE.getUserDTO(pageOutput.getContent()), responseHeader, HttpStatus.OK);

    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        User target = userService.getUser(id);

        if (user != null && target != null) {
            if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_ADMIN)) {
                if(user.equals(target)){
                    userService.deleteUserById(id);
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }else if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
                    Farm ownerAffiliation = target.getAffiliation();
                    farmService.deleteFarmById(ownerAffiliation.getId());
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }else if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)) {
                    userService.deleteUserById(id);
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                }
            }
            else if (user.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_OWNER)) {
                if(user.equals(target)){
                    Farm ownerAffiliation = user.getAffiliation();
                    farmService.deleteFarmById(ownerAffiliation.getId());
                    return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                }
                else if (target.getAuthorities().get(0).getName().equals(AuthorityName.ROLE_EMPLOYEE)){
                    Farm ownerAffiliation = user.getAffiliation();
                    Farm employeeAffiliation = target.getAffiliation();
                    if (ownerAffiliation.equals(employeeAffiliation)) {
                        userService.deleteUserById(id);
                        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDTO(target));
                    }
                    else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                    }
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }
}
