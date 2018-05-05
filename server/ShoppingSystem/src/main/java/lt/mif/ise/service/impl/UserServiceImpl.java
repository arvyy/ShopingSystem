package lt.mif.ise.service.impl;

import lt.mif.ise.domain.Product;
import lt.mif.ise.domain.Role;
import lt.mif.ise.domain.User;
import lt.mif.ise.jpa.RoleRepository;
import lt.mif.ise.jpa.UserRepository;
import lt.mif.ise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

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
        if (userRepository.count() == 0) {
            // test user
            User u1 = new User();
            u1.setEnabled(true);
            u1.setId(UUID.randomUUID().toString());
            u1.setEmail("testUser@gmail.com");
            u1.setPassword(bCryptPasswordEncoder.encode("testUser"));
            Set<Role> userRole = roleRepository.findByName("ROLE_USER");
            if (null == userRole || 0 == userRole.size()){
                Role newRole = createNewRole(1l, "ROLE_USER");
                newRole.setUsers(new HashSet<User>(Arrays.asList(u1)));
                roleRepository.save(newRole);
                userRole.add(newRole);
            }
            u1.setRoles(userRole);

            //admin user
            User u2 = new User();
            u2.setEnabled(true);
            u2.setId(UUID.randomUUID().toString());
            u2.setEmail("admin@gmail.com");
            u2.setPassword(bCryptPasswordEncoder.encode("admin"));
            Set<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (null == adminRole || 0 == adminRole.size()){
                Role newAdmRole = createNewRole(2l, "ROLE_ADMIN");
                newAdmRole.setUsers(new HashSet<User>(Arrays.asList(u2)));
                roleRepository.save(newAdmRole);
                adminRole.add(newAdmRole);
            }
            u2.setRoles(adminRole);
            userRepository.saveAll(Arrays.asList(u1, u2));
        }
    }

    @Override
    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
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
