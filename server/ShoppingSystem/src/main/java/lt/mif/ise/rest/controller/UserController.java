package lt.mif.ise.rest.controller;

import lt.mif.ise.validator.UserValidator;
import lt.mif.ise.domain.User;
import lt.mif.ise.service.SecurityService;
import lt.mif.ise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout){
        if(error != null){
            model.addAttribute("error", "Invalid username or password.");
        }

        if (logout != null){
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @RequestMapping(value={"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model){
        return "welcome";
    }
}
