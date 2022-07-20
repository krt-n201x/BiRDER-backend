package birderbackend.project.rest.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


import birderbackend.project.rest.dao.UserDao;
import birderbackend.project.rest.security.entity.User;
import birderbackend.project.rest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.platform.runner.JUnitPlatform;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
//@RunWith(SpringJUnit4ClassRunner.class)

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserDao userDao;

    @Mock
    UserService userService;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testAddEmployeeTestCase1() throws ResponseStatusException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        User user = new User();
        user.setUsername("sam");
        user.setPassword("sam01");
        user.setEmail("sam01@gmail.com");
        user.setAddress("111 M.2 A.Mueng P.Chiang Mai Thailand 50000");
        user.setPhoneNumber("081-8000000");
        user.setFullName("Sam Samala");
        user.setEnabled(true);
        user.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("sam")).thenReturn(null);
        when(userService.save(user)).thenReturn(user);
//        String input = mapToJson(user);
        ResponseEntity<?> responseEntity = userController.addUser(user);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testAddEmployeeTestCase2() throws ResponseStatusException {
        User userCase2 = new User();
        userCase2.setUsername("smith");
        userCase2.setPassword("smith01");
        userCase2.setEmail("sam01@gmail.com");
        userCase2.setAddress("111 M.2 A.Mueng P.Chiang Mai Thailand 50000");
        userCase2.setPhoneNumber("081-8000000");
        userCase2.setFullName("Sam Samala");
        userCase2.setEnabled(true);
        userCase2.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("sam")).thenReturn(userCase2);
//        when(userService.save(userCase2)).thenReturn(userCase2);
//        String input = mapToJson(user);
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Could not register, some data might not correct.");
        try{
            userController.addUser(userCase2);
        }
        catch (ResponseStatusException msg){
            assertThat(msg.getMessage()).isEqualTo("502 BAD_GATEWAY \"Could not register, some data might not correct.\"");
        }
    }

    @Test
    public void testAddEmployeeTestCase3() throws ResponseStatusException {
        User userCase3 = new User();
        userCase3.setUsername("");
        userCase3.setPassword("sam01");
        userCase3.setEmail("sam01@gmail.com");
        userCase3.setAddress("111 M.2 A.Mueng P.Chiang Mai Thailand 50000");
        userCase3.setPhoneNumber("081-8000000");
        userCase3.setFullName("Sam Samala");
        userCase3.setEnabled(true);
        userCase3.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("")).thenReturn(userCase3);
//        when(userService.save(userCase3)).thenReturn(userCase3);
//        String input = mapToJson(user);
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Could not register, some data might not correct.");
        try{
            userController.addUser(userCase3);
        }
        catch (ResponseStatusException msg){
            assertThat(msg.getMessage()).isEqualTo("502 BAD_GATEWAY \"Could not register, some data might not correct.\"");
        }
    }

    @Test
    public void testAddEmployeeTestCase4() throws ResponseStatusException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User userCase4 = new User();
        userCase4.setUsername("");
        userCase4.setPassword("");
        userCase4.setEmail("");
        userCase4.setAddress("");
        userCase4.setPhoneNumber("");
        userCase4.setFullName("");
        userCase4.setEnabled(true);
        userCase4.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("")).thenReturn(userCase4);
//        when(userService.save(userCase3)).thenReturn(userCase3);
//        String input = mapToJson(user);
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Could not register, some data might not correct.");
        try{
            userController.addUser(userCase4);
        }
        catch (ResponseStatusException msg){
            assertThat(msg.getMessage()).isEqualTo("502 BAD_GATEWAY \"Could not register, some data might not correct.\"");
        }
    }

//    Add Farm Employee
    @Test
    public void testAddFarmEmployeeTestCase1() throws ResponseStatusException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        User user = new User();
        user.setUsername("sammi");
        user.setPassword("sammi01");
        user.setEmail("sammi01@gmail.com");
        user.setAddress("171 M.2 A.Mueng P.Chiang Mai Thailand 50000");
        user.setPhoneNumber("081-7000000");
        user.setFullName("Sammi Samalian");
        user.setEnabled(true);
        user.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    //        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("sammi")).thenReturn(null);
        when(userService.save(user)).thenReturn(user);
    //        String input = mapToJson(user);
        ResponseEntity<?> responseEntity = userController.addUser(user);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testAddFarmEmployeeTestCase2() throws ResponseStatusException {
        User userCase2 = new User();
        userCase2.setUsername("smith");
        userCase2.setPassword("smith01");
        userCase2.setEmail("sammi01@gmail.com");
        userCase2.setAddress("171 M.2 A.Mueng P.Chiang Mai Thailand 50000");
        userCase2.setPhoneNumber("081-7000000");
        userCase2.setFullName("Sammi Samalian");
        userCase2.setEnabled(true);
        userCase2.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("smith")).thenReturn(userCase2);
//        when(userService.save(userCase2)).thenReturn(userCase2);
//        String input = mapToJson(user);
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Could not register, some data might not correct.");
        try{
            userController.addUser(userCase2);
        }
        catch (ResponseStatusException msg){
            assertThat(msg.getMessage()).isEqualTo("502 BAD_GATEWAY \"Could not register, some data might not correct.\"");
        }
    }

    @Test
    public void testAddFarmEmployeeTestCase3() throws ResponseStatusException {
        User userCase3 = new User();
        userCase3.setUsername("");
        userCase3.setPassword("sammi01");
        userCase3.setEmail("sammi01@gmail.com");
        userCase3.setAddress("171 M.2 A.Mueng P.Chiang Mai Thailand 50000");
        userCase3.setPhoneNumber("081-7000000");
        userCase3.setFullName("Sammi Samalian");
        userCase3.setEnabled(true);
        userCase3.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("")).thenReturn(userCase3);
//        when(userService.save(userCase3)).thenReturn(userCase3);
//        String input = mapToJson(user);
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Could not register, some data might not correct.");
        try{
            userController.addUser(userCase3);
        }
        catch (ResponseStatusException msg){
            assertThat(msg.getMessage()).isEqualTo("502 BAD_GATEWAY \"Could not register, some data might not correct.\"");
        }
    }

    @Test
    public void testAddFarmEmployeeTestCase4() throws ResponseStatusException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User userCase4 = new User();
        userCase4.setUsername("");
        userCase4.setPassword("");
        userCase4.setEmail("");
        userCase4.setAddress("");
        userCase4.setPhoneNumber("");
        userCase4.setFullName("");
        userCase4.setEnabled(true);
        userCase4.setLastPasswordResetDate(Date.from(LocalDate.of(2021, 01, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        when(userDao.save(user)).thenReturn(user);
        when(userService.findByUsername("")).thenReturn(userCase4);
//        when(userService.save(userCase3)).thenReturn(userCase3);
//        String input = mapToJson(user);
        exceptionRule.expect(ResponseStatusException.class);
        exceptionRule.expectMessage("Could not register, some data might not correct.");
        try{
            userController.addUser(userCase4);
        }
        catch (ResponseStatusException msg){
            assertThat(msg.getMessage()).isEqualTo("502 BAD_GATEWAY \"Could not register, some data might not correct.\"");
        }
    }


//  View Profile Detail
    @Test
    void viewProfileDetail() {

    }

    @Test
    void updateProfileDetail() {
    }

    @Test
    void getFarmList() {
    }

    @Test
    void getFarmEmployeeList() {
    }

    @Test
    void getSearchFarmList() {
    }

    @Test
    void getSearchFarmEmployeeList() {
    }

    @Test
    void deleteAccount() {
    }
}