package ex8_CC_SpringBoot.demo.DTO;
import ex8_CC_SpringBoot.demo.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(
                user.getName(),
                user.getSurname(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getFiscalCode()
        );
    }

    public User fromDto(UserDto userDto){
       User user = new User();

       user.setName(userDto.getName());
       user.setSurname(userDto.getSurname());
       user.setAddress(userDto.getAddress());
       user.setPhoneNumber(userDto.getPhoneNumber());
       user.setFiscalCode(userDto.getFiscalCode());

       return user;
    }
}
