package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.UserDTO;
import ex8_CC_SpringBoot.demo.DTO.UserMapper;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public double getBalanceByAccountNumber(Long accountNumber) {
        return userRepository.findBalanceByAccountNumber(accountNumber);
    }

    public void addUser(UserDTO userDto) {
        User user = userMapper.fromDto(userDto);
        userRepository.save(user);
    }

    public void updateBalance(User user, double amount) {
        double currentBalance = user.getBalance();
        double newBalance = currentBalance + amount;
        user.setBalance(newBalance);
        userRepository.save(user);
    }
}
