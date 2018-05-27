package lt.mif.ise.security;

import lt.mif.ise.domain.User;
import lt.mif.ise.service.UserService;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class UserValidator{
    @Autowired
    private UserService userService;

    public String validate(Object o, BindingResult errors, boolean isNew) {
        User user = (User) o;

        String result = "";
        if (isNew) {
            if (userService.findByEmail(user.getEmail()) != null) {
                errors.addError(new ObjectError("email", "Email already exist.\n"));
                result += "Email already exist.\n";
            }
        }

        if (user.getPassword().length() < 8) {
            errors.addError(new ObjectError("password", "Password should be longer than 7 characters.\n"));
            result += "Password should be longer than 7 characters.\n";
        }
        return result;
    }
}
