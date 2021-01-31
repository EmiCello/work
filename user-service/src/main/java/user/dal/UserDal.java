package user.dal;

import user.dao.UserDao;
import user.domains.UpdateUserRequest;
import user.domains.User;

public interface UserDal extends UserDao<User> {
    User findByUserEmail(String userEmail);
    User updateById(String id, UpdateUserRequest request);
}

