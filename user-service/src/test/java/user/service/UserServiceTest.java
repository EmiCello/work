package user.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.dal.UserDalImpl;
import user.domains.UpdateUserRequest;
import user.domains.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static user.helper.UserServiceUtils.getAllUsers;
import static user.helper.UserServiceUtils.getUser;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDalImpl dal;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void testGetAll() {
        List<User> users = getAllUsers();
        when(dal.getAll()).thenReturn(users);

        List<User> result = service.getAll();

        assertNotNull(result, "Result must not be null");
        assertEquals(2, result.size(), "Result list size of all users is not as expected.");

        verify(dal, times(1)).getAll();
    }

    @Test
    public void testCreate() {
        User user = getUser();

        when(dal.create(any(User.class))).thenReturn(user);

        User result = service.create(user);

        assertNotNull(result, "Result must not be null.");
        assertEquals("johny_english@gmail.com", result.getEmail(), "User's email is not as expected.");
        assertEquals("John", result.getFirstName(), "User's first name is not as expected.");
        assertEquals("English", result.getLastName(), "User's last name is not as expected.");
        assertEquals(24, result.getAge(), "User's age is not as expected.");

        verify(dal, times(1)).create(any(User.class));
    }

    @Test
    public void testFindById() {
        User user = getUser();
        String id = "11id";

        when(dal.findById(id)).thenReturn(user);

        User result = service.findById(id);

        assertNotNull(result, "Result must not be null.");
        assertEquals("johny_english@gmail.com", result.getEmail(), "User's email is not as expected.");
        assertEquals("John", result.getFirstName(), "User's first name is not as expected.");
        assertEquals("English", result.getLastName(), "User's last name is not as expected.");
        assertEquals(24, result.getAge(), "User's age is not as expected.");

        verify(dal, times(1)).findById(id);
    }

    @Test
    public void testDeleteById() {
        User user = getUser();
        String id = "11id";

        when(dal.deleteById(id)).thenReturn(user);

        User result = service.deleteById(id);

        assertNotNull(result, "Result must not be null.");
        assertEquals("johny_english@gmail.com", result.getEmail(), "User's email is not as expected.");
        assertEquals("John", result.getFirstName(), "User's first name is not as expected.");
        assertEquals("English", result.getLastName(), "User's last name is not as expected.");
        assertEquals(24, result.getAge(), "User's age is not as expected.");

        verify(dal, times(1)).deleteById(id);
    }

    @Test
    public void testFindByUserEmail() {
        User user = getUser();
        String email = "johny_english@gmail.com";

        when(dal.findByUserEmail(email)).thenReturn(user);

        User result = service.findByUserEmail(email);

        assertNotNull(result, "Result must not be null.");
        assertEquals("johny_english@gmail.com", result.getEmail(), "User's email is not as expected.");
        assertEquals("John", result.getFirstName(), "User's first name is not as expected.");
        assertEquals("English", result.getLastName(), "User's last name is not as expected.");
        assertEquals(24, result.getAge(), "User's age is not as expected.");

        verify(dal, times(1)).findByUserEmail(email);
    }

    @Test
    public void testUpdateById() {
        User updatedUser = User.builder()
            .id("11id")
            .email("johny_english@gmail.com")
            .firstName("John")
            .lastName("English")
            .age(30)
            .build();
        String id = "11id";
        UpdateUserRequest request = UpdateUserRequest.builder()
            .age("30")
            .build();

        when(dal.updateById(id, request)).thenReturn(updatedUser);

        User result = service.updateById(id, request);

        assertNotNull(result, "Result must not be null.");
        assertEquals("johny_english@gmail.com", result.getEmail(), "User's email is not as expected.");
        assertEquals("John", result.getFirstName(), "User's first name is not as expected.");
        assertEquals("English", result.getLastName(), "User's last name is not as expected.");
        assertEquals(30, result.getAge(), "User's age is not as expected.");
        assertEquals("11id", result.getId(), "User's id is not as expected.");
    }
}