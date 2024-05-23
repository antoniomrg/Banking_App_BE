package ex8_CC_SpringBoot.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ex8_CC_SpringBoot.demo.DTO.UserDto;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import ex8_CC_SpringBoot.demo.Service.UserServiceImpl;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class UserControllerItTest {

    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static Long USER_ID;
    private static Long USER_ID_NOT_EXISTING = 999L;

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private UserServiceImpl userServiceImpl;

        @Autowired
        private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
//        userRepository.deleteAll();

        var user = User.builder()
                .userId(1L)
                .name(NAME)
                .surname(SURNAME)
                .build();

        // Dato che la strategia di generazione dell'ID è IDENTITY, devo recuperare l'ID dopo aver salvato
        // l'utente nel DB
        userRepository.save(user);
        //USER_ID = user.getUserId();
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                 .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(NAME))
                .andExpect(jsonPath("$[0].surname").value(SURNAME));
    }

    @Test
    void getUserById() throws Exception {

//                var userDto = UserDto.builder()
//                .name(NAME)
//                .surname(SURNAME)
//                .build();
//
//                when(userServiceImpl.getUserById(any())).thenReturn(userDto);

        mockMvc.perform(get("/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.surname").value(SURNAME));
    }

    @Test
    void addUser() throws Exception {
        // Read data from JSON file
        InputStream inputStream = FileUtils.openInputStream(new File("C:\\Users\\black\\Dropbox (Personale)\\Coding\\Aitho\\Backender 101\\Ex_8_CC_SpringBoot\\demo\\src\\test\\resources\\utils\\request.json"));
        UserDto userDto = new ObjectMapper().readValue(inputStream, UserDto.class);

        // Perform POST request
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));
    }

    @Test
    void getUserNotExisting() throws Exception {
        mockMvc.perform(get("/users/{userId}", USER_ID_NOT_EXISTING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}