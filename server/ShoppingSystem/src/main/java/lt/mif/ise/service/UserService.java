package lt.mif.ise.service;

import lt.mif.ise.domain.User;

public interface UserService {
    void save(User user);

    User findByEmail(String email);

    Iterable<User> getAll();

    void disableByEmail(String email);

    void enableByEmail(String email);

    void disableById(String id);

    void enableById(String id);
}
