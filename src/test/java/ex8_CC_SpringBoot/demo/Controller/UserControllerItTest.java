package ex8_CC_SpringBoot.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ex8_CC_SpringBoot.demo.DTO.UserDto;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerItTest {

    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static Long USER_ID;
    private static Long USER_ID_NOT_EXISTING = 999L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        val userList = User.builder()
                .name(NAME)
                .surname(SURNAME)
                .build();

        userRepository.save(userList);
        USER_ID = userList.getUserId();
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
        mockMvc.perform(get("/users/{userId}", USER_ID)
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