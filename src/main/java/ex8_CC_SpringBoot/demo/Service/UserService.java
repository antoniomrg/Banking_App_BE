package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.UserDTO;
import ex8_CC_SpringBoot.demo.Entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    double getBalanceByAccountNumber(Long accountNumber);
    void addUser(UserDTO userDto);
    void updateBalance(User user, double amount);
}
