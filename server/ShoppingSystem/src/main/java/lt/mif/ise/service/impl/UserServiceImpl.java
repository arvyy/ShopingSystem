package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Role;
import lt.mif.ise.domain.User;
import lt.mif.ise.jpa.RoleRepository;
import lt.mif.ise.jpa.UserRepository;
import lt.mif.ise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Role createNewRole(Long id, String name){
        Role newRole = new Role();
        newRole.setName(name);
        newRole.setId(id);
        roleRepository.save(newRole);
        return newRole;
    }

    @PostConstruct
    public void init() {
    }

    @Override
    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user){
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Iterable<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public void disableByEmail(String email){
        User user = userRepository.findByEmail(email);

        if (null != user){
            user.setEnabled(false);
            userRepository.save(user);
        }
    }

    @Override
    public void enableByEmail(String email){
        User user = userRepository.findByEmail(email);

        if (null != user){
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    @Override
    public void disableById(String id){
        User user = userRepository.findById(id).get();

        if (null != user){
            user.setEnabled(false);
            userRepository.save(user);
        }
    }

    @Override
    public void enableById(String id){
        User user = userRepository.findById(id).get();

        if (null != user){
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

}
