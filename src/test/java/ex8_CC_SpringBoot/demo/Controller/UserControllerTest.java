package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.UserDto;
import ex8_CC_SpringBoot.demo.Service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto buildFakeUserDto(){
        return UserDto.builder()
                .name("Name")
                .surname("Surname")
                .address("Address")
                .phoneNumber("Phone Number")
                .fiscalCode("Fiscal code")
                .build();
    }

    @Test
    void addUser() {
        val response = userController.addUser(buildFakeUserDto());

        assertEquals("User added successfully", response.getBody());
    }

    @Test
    void getAllUsers() {
        val fakeList = List.of(buildFakeUserDto());

        when(userService.getAllUsers()).thenReturn(fakeList);

        val response = userController.getAllUsers();

        assertEquals(fakeList, response.getBody());
    }

    @Test
    void getUserById() {
        when(userService.getUserById(any())).thenReturn(buildFakeUserDto());

        val response = userController.getUserById(1L);

        assertEquals(buildFakeUserDto(), response.getBody());
    }
}