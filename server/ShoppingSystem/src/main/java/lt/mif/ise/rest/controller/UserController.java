package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.User;
import lt.mif.ise.security.UserValidator;
import lt.mif.ise.service.SecurityService;
import lt.mif.ise.service.UserService;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/users/")
@RestController
public class UserController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value="sign-up", method = RequestMethod.POST)
    public String signUp(@RequestBody @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "error registering user";
        }

        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);
        userService.save(user);

        securityService.autologin(user.getUsername(), user.getPassword());

        return "user registered";
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
