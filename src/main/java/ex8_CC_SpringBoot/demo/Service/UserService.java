package ex8_CC_SpringBoot.demo.Service;
import ex8_CC_SpringBoot.demo.DTO.UserDTO;
import ex8_CC_SpringBoot.demo.Entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);
    User addUser(User user);
    double getBalanceByAccountNumber(Long accountNumber);
    void makeDeposit(Long id, double amount);
    void makeWithdrawal(Long id, double amount);
}
