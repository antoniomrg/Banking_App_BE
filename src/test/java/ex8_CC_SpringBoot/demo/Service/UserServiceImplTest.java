package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.UserDto;
import ex8_CC_SpringBoot.demo.DTO.UserMapper;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void addUser() {

        when(userMapper.fromDto(any())).thenReturn(fakeUser());
        when(userRepository.save(any())).thenReturn(fakeUser());

        assertDoesNotThrow(() -> userServiceImpl.addUser(fakeUserDto()));
    }

    @Test
    void getAllUsers() {

        when(userRepository.findAll()).thenReturn(List.of(fakeUser()));
        when(userMapper.toDto(any())).thenReturn(fakeUserDto());

        val out = userServiceImpl.getAllUsers();

        val firstElement = out.get(0);
        assertNotNull(firstElement);
        assertEquals("Name", firstElement.getName());
    }

    @Test
    void getUserById() {

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(fakeUser()));
        when(userMapper.toDto(any())).thenReturn(fakeUserDto());

        val out = userServiceImpl.getUserById(fakeUser().getUserId());

        assertNotNull(out);
        assertEquals("Name", out.getName());
    }

    private UserDto fakeUserDto(){
        return UserDto.builder()
                .name("Name")
                .surname("Surname")
                .address("Address")
                .phoneNumber("Phone Number")
                .fiscalCode("Fiscal code")
                .build();
    }

    private User fakeUser(){
        return User.builder()
                .name("Name")
                .surname("Surname")
                .address("Address")
                .phoneNumber("Phone Number")
                .fiscalCode("Fiscal code")
                .build();
    }
}