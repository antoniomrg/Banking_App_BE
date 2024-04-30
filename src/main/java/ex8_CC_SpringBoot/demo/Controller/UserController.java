package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.UserDTO;
import ex8_CC_SpringBoot.demo.Service.BankTransactionService;
import ex8_CC_SpringBoot.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("users")
@RestController
public class UserController {

    private final UserService userService;
    private final BankTransactionService bankTransactionService;

    public UserController(UserService userService, BankTransactionService bankTransactionService) {
        this.userService = userService;
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping(path = "{userId}/history")
    public ResponseEntity<?> getLastFiveTransactions(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(bankTransactionService.getLastFiveTransactions(userId));

    }

    @GetMapping(path = ("/balance"))
    public ResponseEntity<?> getBalanceByAccountNumber(@RequestParam Long accountNumber) {
        return ResponseEntity.ok(userService.getBalanceByAccountNumber(accountNumber));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok().body("User added successfully");
    }

    @PutMapping(path = "{userId}/deposit")
    public ResponseEntity<?> makeDeposit(@PathVariable("userId") Long userId, @RequestParam double amount) {
        bankTransactionService.makeDeposit(userId, amount);
        return ResponseEntity.ok().body("You added " + amount + " to your account");
    }

    @PutMapping(path = "{userId}/withdrawal")
    public ResponseEntity<?> makeWithdrawal(@PathVariable("userId") Long userId, @RequestParam double amount) {
        bankTransactionService.makeWithdrawal(userId, amount);
        return ResponseEntity.ok().body("You withdrew " + amount + " from your account");
    }

}
