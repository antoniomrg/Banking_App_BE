package ex8_CC_SpringBoot.demo.DTO;
import ex8_CC_SpringBoot.demo.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return new UserDTO(
                user.getAccountNumber(),
                user.getName(),
                user.getSurname(),
                user.getIban(),
                user.getBalance()
        );
    }

    public User fromDto(UserDTO userDto){
       User user = new User();

       user.setAccountNumber(userDto.getAccountNumber());
       user.setName(userDto.getName());
       user.setSurname(userDto.getSurname());
       user.setIban(userDto.getIban());
       user.setBalance(userDto.getBalance());

       return user;
    }
}
