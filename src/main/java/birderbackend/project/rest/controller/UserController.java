package birderbackend.project.rest.controller;

import birderbackend.project.rest.entity.Farm;
import birderbackend.project.rest.repository.FarmRepository;
import birderbackend.project.rest.security.JwtTokenUtil;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.security.repository.UserRepository;
import birderbackend.project.rest.service.UserService;
import birderbackend.project.rest.util.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
