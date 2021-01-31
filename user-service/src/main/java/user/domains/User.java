package user.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Document(collection = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    private String id;

    @Email(message = "Email is not in proper format.")
    @Indexed(name = "user_email", unique = true)
    private String email;
    @NotBlank(message = "User first name is empty or null.")
    private String firstName;
    @NotBlank(message = "User last name is empty or null.")
    private String lastName;
    private int age;
}
