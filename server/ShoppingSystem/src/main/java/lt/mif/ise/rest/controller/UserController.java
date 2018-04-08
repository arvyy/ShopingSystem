package lt.mif.ise.rest.controller;

import lt.mif.ise.validator.UserValidator;
import lt.mif.ise.domain.User;
import lt.mif.ise.service.SecurityService;
import lt.mif.ise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value={"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(){
        return "welcome";
    }
}
