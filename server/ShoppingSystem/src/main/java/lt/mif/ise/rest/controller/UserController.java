package lt.mif.ise.rest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lt.mif.ise.domain.EmailUpdateDto;
import lt.mif.ise.domain.PasswordUpdateDto;
import lt.mif.ise.domain.User;
import lt.mif.ise.error.exception.BadRequestException;
import lt.mif.ise.error.exception.NotFoundException;
import lt.mif.ise.security.MyUserDetails;
import lt.mif.ise.security.UserValidator;
import lt.mif.ise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
        String validationMessage = userValidator.validate(user, bindingResult, true);

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(validationMessage);
        }

        String email = user.getEmail();
        String password = user.getPassword();

        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);
        userService.save(user);

        autoLogin(email, password, request);

        return new ResponseEntity(HttpStatus.OK);
    }

    public boolean autoLogin( String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication );
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return true;
    }

    @PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('USER')")
    @RequestMapping(value = "update/email", method = RequestMethod.POST)
    public ResponseEntity updateUserEmail(@RequestBody @Valid EmailUpdateDto emailDto, BindingResult result){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findByEmail(userDetails.getUsername());
        if (null == user){
            throw new NotFoundException(String.format("User %s not found", userDetails.getUsername()));
        }
		if (userService.findByEmail(emailDto.getEmail()) != null) {
            throw new BadRequestException("Email already exists.\n");
		}
        user.setEmail(emailDto.getEmail());
        String validationMessage = userValidator.validate(user, result, false);
        if (result.hasErrors()){
            throw new BadRequestException(validationMessage);
        }
        userService.updateUser(user);
        userDetails.setUserEmail(user.getEmail());

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN') or hasAnyRole('USER')")
    @RequestMapping(value = "update/password", method = RequestMethod.POST)
    public ResponseEntity updateUserPassword(@RequestBody PasswordUpdateDto passwordDto, BindingResult result){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findByEmail(userDetails.getUsername());
        if (null == user){
            throw new NotFoundException(String.format("User %s not found", userDetails.getUsername()));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            passwordDto.getOldPassword()
                    )
            );
        }
        catch (AuthenticationException ae){
            throw new BadRequestException(String.format("User %s password is incorrect", user.getEmail()));
        }

        if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())){
            throw new BadRequestException("Password confirm does not match");
        }

        user.setPassword(passwordDto.getConfirmPassword());
        String validationMessage = userValidator.validate(user, result, false);
        if (result.hasErrors()){
            throw new BadRequestException(validationMessage);
        }
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
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
}
