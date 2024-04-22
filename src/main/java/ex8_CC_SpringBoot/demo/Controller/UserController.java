package ex8_CC_SpringBoot.demo.Controller;
import ex8_CC_SpringBoot.demo.DTO.UserDTO;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("bankaccount/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public Optional<UserDTO> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping(path = ("/balance"))
    public double getBalanceByAccountNumber(@RequestParam Long accountNumber) {
        return userService.getBalanceByAccountNumber(accountNumber);
    }

    @PutMapping(path = "{id}/deposit")
    public void makeDeposit(@PathVariable("id") Long id, @RequestParam double amount) {
        userService.makeDeposit(id, amount);
    }

    @PutMapping(path = "{id}/withdrawal")
    public void makeWithdrawal(@PathVariable("id") Long id, @RequestParam double amount) {
        userService.makeWithdrawal(id, amount);
    }


}
