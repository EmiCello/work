package user.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import user.HttpException;
import user.domains.UpdateUserRequest;
import user.domains.User;

import java.util.List;

@Repository
public class UserDalImpl implements UserDal {

    @Autowired
    private MongoOperations mongoTemplate;

    @Override
    public List<User> getAll() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User create(User user) {
        User createdUser = mongoTemplate.insert(user);

        if (createdUser == null) {
            throw HttpException.builder()
                .httpStatus(500)
                .errorMessage("Unknown problem during the user creating.")
                .build();
        }
        return createdUser;
    }

    @Override
    public User findById(String id) {
        User foundUser =  mongoTemplate.findById(id, User.class);

        if (foundUser == null) {
            throw HttpException.builder()
                .httpStatus(404)
                .errorMessage("User with id " + id + " was not found.")
                .build();
        }
        return foundUser;
    }

    @Override
    public User deleteById(String id) {
        User foundUser = mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), User.class);

        if (foundUser == null) {
            throw HttpException.builder()
                .httpStatus(404)
                .errorMessage("User with id " + id + " was not found.")
                .build();
        }
        return foundUser;
    }

    @Override
    public User findByUserEmail(String userEmail) {
        User foundUser = mongoTemplate.findOne(Query.query(Criteria.where("email").is(userEmail)), User.class);

        if (foundUser == null) {
            throw HttpException.builder()
                .httpStatus(404)
                .errorMessage("User with email " + userEmail + " was not found.")
                .build();
        }
        return foundUser;
    }

    @Override
    public User updateById(String id, UpdateUserRequest request) {
        User userToUpdate = mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), User.class);
        if (userToUpdate == null) {
            throw HttpException.builder()
                .httpStatus(404)
                .errorMessage("User with id " + id + " was not found.")
                .build();
        }
        if (request.getFirstName() != null) {
            userToUpdate.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            userToUpdate.setFirstName(request.getLastName());
        }
        if (request.getAge() != null) {
            userToUpdate.setAge(Integer.parseInt(request.getAge()));
        }
        return mongoTemplate.save(userToUpdate);
    }
}
