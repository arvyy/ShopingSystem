package lt.mif.ise.service;

import lt.mif.ise.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
