package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.EmailUpdateDto;
import lt.mif.ise.domain.PasswordUpdateDto;
import lt.mif.ise.domain.User;
import lt.mif.ise.security.UserValidator;
import lt.mif.ise.service.UserService;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RequestMapping("/api/user/")
@RestController
public class UserController {
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;
    
    private ObjectMapper om;
    
    public UserController() {
    	om = new ObjectMapper();
    }

    @RequestMapping(value="sign-up", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody @Valid User user, BindingResult bindingResult, HttpServletRequest request){
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = user.getEmail();
        String password = user.getPassword();

        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);
        userService.save(user);

        autoLogin(email, password, request);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public boolean autoLogin( String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication );
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return true;
    }

    @PreAuthorize(("hasAnyRole('USER')"))
    @RequestMapping(value = "update/email", method = RequestMethod.POST)
    public ResponseEntity updateUserEmail(@RequestBody EmailUpdateDto emailDto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findByEmail(userDetails.getUsername());
        if (null == user){
            throw new UsernameNotFoundException(null);
        }

        user.setEmail(emailDto.getEmail());
        userService.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PreAuthorize(("hasAnyRole('USER')"))
    @RequestMapping(value = "update/password", method = RequestMethod.POST)
    public ResponseEntity updateUserPassword(@RequestBody PasswordUpdateDto passwordDto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findByEmail(userDetails.getUsername());
        if (null == user){
            throw new UsernameNotFoundException(null);
        }

        if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())){
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }

        user.setPassword(passwordDto.getConfirmPassword());
        userService.save(user);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    
    @RequestMapping("me")
    public JsonNode me(UsernamePasswordAuthenticationToken user) {
    	ObjectNode n = om.createObjectNode();
    	n.put("name", user != null? user.getName() : null);
    	n.put("admin", user != null && user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    	return n;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="allusers", method = RequestMethod.GET)
    public Iterable<User> getAllUsers(){
        return userService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "disable/{email}", method = RequestMethod.POST)
    public void disableUserByEmail(@PathVariable(value= "email") String email){
        userService.disableByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "enable/{email}", method = RequestMethod.POST)
    public void enableUserByEmail(@PathVariable(value = "email") String email){
        userService.enableByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "disable/{id}", method = RequestMethod.POST)
    public void disableUserById(@PathVariable(value= "id") String id){
        userService.disableById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "enable/{id}", method = RequestMethod.POST)
    public void enableUserById(@PathVariable(value = "id") String id){
        userService.enableById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('USER')")
    @RequestMapping(value="test/secured/admin", method = RequestMethod.GET)
    public String testAdminAuthorization(){
        return "welcome ADMIN";
    }

    @PreAuthorize(("hasAnyRole('USER')"))
    @RequestMapping(value="test/secured/user", method = RequestMethod.GET)
    public String testUserAuthorization(){
        return "welcome USER";
    }

    @RequestMapping(value = "test/user", method = RequestMethod.GET)
    public String testNonAuthorizedUser(){
        return "welcome non authorized";
    }
}
