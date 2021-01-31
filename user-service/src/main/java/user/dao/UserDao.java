package user.dao;

import java.util.List;

public interface UserDao<T> {

    List<T> getAll();
    T create(T t);
    T findById(String id);
    T deleteById(String id);
}
