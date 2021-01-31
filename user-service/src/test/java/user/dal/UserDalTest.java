package user.dal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.HttpException;
import user.domains.UpdateUserRequest;
import user.domains.User;
import user.test.config.TestConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static user.helper.UserDalUtils.getAllUsers;
import static user.helper.UserDalUtils.getUser;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Import(TestConfig.class)
public class UserDalTest {

    @Autowired
    private UserDalImpl dal;
    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void testGetAll() {
        List<User> users = getAllUsers();
        when(mongoTemplate.findAll(User.class)).thenReturn(users);
        List<User> allUsers = dal.getAll();
        verify(mongoTemplate, times(1)).findAll(User.class);

        assertNotNull(allUsers, "List of all users must not be null");
        assertEquals(2, allUsers.size(), "List's size of all users is not as expected");
    }

    @Test
    public void testCreate() {
        User user = getUser();
        when(mongoTemplate.insert(user)).thenReturn(user);
        User result = dal.create(user);
        verify(mongoTemplate, times(1)).insert(user);

        assertNotNull(result, "Result must not be null");
        assertEquals("11id", result.getId(), "User Id doesn't match");
        assertEquals("johny_english@gmail.com", result.getEmail(), "Email address doesn't match");
        assertEquals("John", result.getFirstName(), "User first name doesn't match");
        assertEquals("English", result.getLastName(), "User last name doesn't match");
        assertEquals(24, result.getAge(), "User age is not as expected.");
    }

    @Test
    public void testCreateFailed() {
        User user = getUser();
        when(mongoTemplate.insert(user)).thenReturn(null);
        HttpException exception = assertThrows(HttpException.class, () -> {
            dal.create(user);
        });

        assertNotNull(exception, "Result must be null");
        assertEquals(500, exception.getHttpStatus(), "Exception http status is not as expected");
        assertEquals("Unknown problem during the user creating.", exception.getErrorMessage(), "Exception error message is not as expected");
        verify(mongoTemplate, times(1)).insert(user);
    }

    @Test
    public void testFindById() {
        User user = getUser();
        String id = "11id";

        when(mongoTemplate.findById(id, User.class)).thenReturn(user);

        User result = dal.findById(id);

        verify(mongoTemplate, times(1)).findById(id, User.class);

        assertNotNull(result, "Result must not be null");
        assertEquals("11id", result.getId(), "User Id doesn't match");
        assertEquals("johny_english@gmail.com", result.getEmail(), "Email address doesn't match");
        assertEquals("John", result.getFirstName(), "User first name doesn't match");
        assertEquals("English", result.getLastName(), "User last name doesn't match");
        assertEquals(24, result.getAge(), "User age is not as expected.");
    }

    @Test
    public void testFindByIdNotFound() {
        String id = "11id";
        when(mongoTemplate.findById(id, User.class)).thenReturn(null);

        HttpException exception = assertThrows(HttpException.class, () -> {
            dal.findById(id);
        });

        assertNotNull(exception, "Result must be null");
        assertEquals(404, exception.getHttpStatus(), "Exception http status is not as expected");
        assertEquals("User with id " + id + " was not found.", exception.getErrorMessage(), "Exception error message is not as expected");

        verify(mongoTemplate, times(1)).findById(id, User.class);
    }

    @Test
    public void testDeleteById() {
        User user = getUser();
        String id = "11id";
        Query query = Query.query(Criteria.where("id").is(id));

        when(mongoTemplate.findAndRemove(query, User.class)).thenReturn(user);

        User result = dal.deleteById(id);

        verify(mongoTemplate, times(1)).findAndRemove(query, User.class);

        assertNotNull(result, "Result must not be null");
        assertEquals("11id", result.getId(), "User Id doesn't match");
        assertEquals("johny_english@gmail.com", result.getEmail(), "Email address doesn't match");
        assertEquals("John", result.getFirstName(), "User first name doesn't match");
        assertEquals("English", result.getLastName(), "User last name doesn't match");
        assertEquals(24, result.getAge(), "User age is not as expected.");
    }

    @Test
    public void testFindByUserEmail() {
        User user = getUser();
        String userEmail = "johny_english@gmail.com";

        Query query = Query.query(Criteria.where("email").is(userEmail));

        when(mongoTemplate.findOne(query, User.class)).thenReturn(user);

        User result = dal.findByUserEmail(userEmail);

        verify(mongoTemplate, times(1)).findOne(query, User.class);

        assertNotNull(result, "Result must not be null");
        assertEquals("11id", result.getId(), "User Id doesn't match");
        assertEquals("johny_english@gmail.com", result.getEmail(), "Email address doesn't match");
        assertEquals("John", result.getFirstName(), "User first name doesn't match");
        assertEquals("English", result.getLastName(), "User last name doesn't match");
        assertEquals(24, result.getAge(), "User age is not as expected.");
    }

    @Test
    public void testFindByUserEmailNotFound() {
        String userEmail = "johny_english@gmail.com";

        Query query = Query.query(Criteria.where("email").is(userEmail));

        when(mongoTemplate.findOne(query, User.class)).thenReturn(null);

        HttpException exception = assertThrows(HttpException.class, () -> {
            dal.findByUserEmail(userEmail);
        });

        assertEquals(404, exception.getHttpStatus(), "Exception http status is not as expected");
        assertEquals("User with email " + userEmail + " was not found.", exception.getErrorMessage(), "Exception error message is not as expected");

        verify(mongoTemplate, times(1)).findOne(query, User.class);
    }

    @Test
    public void testUpdateById() {
        User updatedUser = getUser();
        updatedUser.setAge(30);
        String id = "11id";
        UpdateUserRequest request = UpdateUserRequest.builder()
            .age("30")
            .build();

        when(mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), User.class)).thenReturn(updatedUser);
        when(mongoTemplate.save(updatedUser)).thenReturn(updatedUser);

        User result = dal.updateById("11id", request);

        assertNotNull(result, "Result must not be null");
        assertEquals("11id", result.getId(), "User Id doesn't match");
        assertEquals("johny_english@gmail.com", result.getEmail(), "Email address doesn't match");
        assertEquals("John", result.getFirstName(), "User first name doesn't match");
        assertEquals("English", result.getLastName(), "User last name doesn't match");
        assertEquals(30, result.getAge(), "User password is not as expected.");

        verify(mongoTemplate, times(1)).findOne(Query.query(Criteria.where("id").is(id)), User.class);
        verify(mongoTemplate, times(1)).save(updatedUser);
    }

    @Test
    public void testUpdateByIdNotFound() {
        String id = "11id";
        UpdateUserRequest request = UpdateUserRequest.builder()
            .age("30")
            .build();

        when(mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), User.class)).thenReturn(null);

        HttpException exception = assertThrows(HttpException.class, () -> {
            dal.updateById("11id", request);
        });

        assertEquals(404, exception.getHttpStatus(), "Exception http status is not as expected");
        assertEquals("User with id 11id was not found.", exception.getErrorMessage(), "Exception error message is not as expected");

        verify(mongoTemplate, times(1)).findOne(Query.query(Criteria.where("id").is(id)), User.class);
        verify(mongoTemplate, times(0)).save(any(User.class));
    }
}
