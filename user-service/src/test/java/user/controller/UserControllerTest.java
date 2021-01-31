package user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import user.dal.UserDalImpl;
import user.domains.UpdateUserRequest;
import user.domains.User;
import user.service.UserServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static user.helper.UserDalUtils.getUser;
import static user.helper.UserServiceUtils.getAllUsers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean(name = "userDetailsService")
    private UserServiceImpl service;
    @Mock
    private UserDalImpl dal;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUpdateUserReturns404() throws Exception {
        String id = "11id";
        UpdateUserRequest request = UpdateUserRequest.builder()
            .age("30")
            .build();

        User updatedUser = getUser();
        updatedUser.setAge(30);

        when(service.updateById(id, request)).thenReturn(null);

        mvc.perform(put("/user/user/11id")
            .content(objectMapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());

        verify(service, times(1)).updateById(id, request);
    }

    @Test
    public void testUpdateUser() throws Exception {
        String id = "11id";
        UpdateUserRequest request = UpdateUserRequest.builder()
            .age("30")
            .build();

        User updatedUser = getUser();
        updatedUser.setAge(30);

        when(service.updateById(id, request)).thenReturn(updatedUser);

        mvc.perform(put("/user/user/11id")
            .content(objectMapper.writeValueAsBytes(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is("11id")))
            .andExpect(jsonPath("$.email", is("johny_english@gmail.com")))
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("English")))
            .andExpect(jsonPath("$.age", is(30)));

        verify(service, times(1)).updateById(id, request);
    }

    @Test
    public void testDeleteUserByIdReturns404() throws Exception {
        when(service.deleteById("11id")).thenReturn(null);

        mvc.perform(delete("/user/user/11id"))
            .andDo(print())
            .andExpect(status().isNotFound());

        verify(service, times(1)).deleteById("11id");
    }

    @Test
    public void testDeleteUserById() throws Exception {
        User user = getUser();

        when(service.deleteById("11id")).thenReturn(user);

        mvc.perform(delete("/user/user/11id"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is("11id")))
            .andExpect(jsonPath("$.email", is("johny_english@gmail.com")))
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("English")))
            .andExpect(jsonPath("$.age", is(24)));

        verify(service, times(1)).deleteById("11id");
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = getUser();

        when(service.findById("11id")).thenReturn(user);

        mvc.perform(get("/user/user/11id"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is("11id")))
            .andExpect(jsonPath("$.email", is("johny_english@gmail.com")))
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("English")))
            .andExpect(jsonPath("$.age", is(24)));

        verify(service, times(1)).findById("11id");
    }

    @Test
    public void testGetUserByIdReturns404() throws Exception {
        when(service.findById("11id")).thenReturn(null);

        mvc.perform(get("/user/user/11id"))
            .andDo(print())
            .andExpect(status().isNotFound());

        verify(service, times(1)).findById("11id");
    }

    @Test
    public void testCreate() throws Exception {
        User user = getUser();

        when(service.create(user)).thenReturn(user);

        mvc.perform(post("/user/user")
            .content(objectMapper.writeValueAsBytes(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is("11id")))
            .andExpect(jsonPath("$.email", is("johny_english@gmail.com")))
            .andExpect(jsonPath("$.firstName", is("John")))
            .andExpect(jsonPath("$.lastName", is("English")))
            .andExpect(jsonPath("$.age", is(24)));

        verify(service, times(1)).create(user);
    }

    @Test
    public void testCreateReturns500() throws Exception {
        User user = getUser();

        when(service.create(user)).thenReturn(null);

        mvc.perform(post("/user/user")
            .content(objectMapper.writeValueAsBytes(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is5xxServerError());

        verify(service, times(1)).create(user);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> allUsers = getAllUsers();
        when(service.getAll()).thenReturn(allUsers);

        mvc.perform(get("/user/users"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is("11id")))
            .andExpect(jsonPath("$[0].email", is("johny_english@gmail.com")))
            .andExpect(jsonPath("$[0].firstName", is("John")))
            .andExpect(jsonPath("$[0].lastName", is("English")))
            .andExpect(jsonPath("$[0].age", is(24)))
            .andExpect(jsonPath("$[1].id", is("22id")))
            .andExpect(jsonPath("$[1].email", is("simon_ambrose@gmail.com")))
            .andExpect(jsonPath("$[1].firstName", is("Simon")))
            .andExpect(jsonPath("$[1].lastName", is("Ambrose")))
            .andExpect(jsonPath("$[1].age", is(30)));

        verify(service, times(1)).getAll();
    }

    @Test
    public void testGetAllUsersReturns500() throws Exception {
        List<User> allUsers = Collections.emptyList();
        when(service.getAll()).thenReturn(allUsers);

        mvc.perform(get("/user/users"))
            .andDo(print())
            .andExpect(status().isNotFound());

        verify(service, times(1)).getAll();
    }

}
