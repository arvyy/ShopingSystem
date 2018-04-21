package lt.mif.ise.rest.controller;

import lt.mif.ise.domain.User;
import lt.mif.ise.service.UserService;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/users/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="sign-up", method = RequestMethod.POST)
    public String signUp(@RequestBody User user){
        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);
        userService.save(user);
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
