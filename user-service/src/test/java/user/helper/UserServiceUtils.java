package user.helper;

import user.domains.User;

import java.util.Arrays;
import java.util.List;

public class UserServiceUtils {
    public static List<User> getAllUsers() {
        return Arrays.asList(User.builder()
                .id("11id")
                .email("johny_english@gmail.com")
                .firstName("John")
                .lastName("English")
                .age(24)
                .build(),
            User.builder()
                .id("22id")
                .email("simon_ambrose@gmail.com")
                .firstName("Simon")
                .lastName("Ambrose")
                .age(30)
                .build());
    }

    public static User getUser() {
        return User.builder()
            .id("11id")
            .email("johny_english@gmail.com")
            .firstName("John")
            .lastName("English")
            .age(24)
            .build();
    }
}
