package user.service;

import user.domains.UpdateUserRequest;
import user.domains.User;

import java.util.List;

public interface UserService {
    List<User> getAll() ;

    User create(User user);

    User findById(String id);

    User deleteById(String id);

    User findByUserEmail(String userEmail);

    User updateById(String id, UpdateUserRequest request);
}
