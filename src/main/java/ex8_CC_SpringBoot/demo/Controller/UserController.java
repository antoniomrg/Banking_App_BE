package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.UserDTO;
import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Service.BankTransactionService;
import ex8_CC_SpringBoot.demo.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
        Optional<UserDTO> user = ResponseEntity.ok(userService.getUserById(userId)).getBody();
        return ResponseEntity.ok(user);
    }


    @GetMapping(path = "{userId}/history")
    public ResponseEntity<?> getLastFiveTransactions(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(bankTransactionService.getLastFiveTransactions(userId));

    }

    @PostMapping
    // DTO
    public void addUser(@RequestBody UserDTO userDto) {
        userService.addUser(userDto);
    }

    @PutMapping(path = "{userId}/deposit")
    public void makeDeposit(@PathVariable("userId") Long userId, @RequestParam double amount) {
        bankTransactionService.makeDeposit(userId, amount);
    }

    @PutMapping(path = "{userId}/withdrawal")
    public void makeWithdrawal(@PathVariable("userId") Long userId, @RequestParam double amount) {
        bankTransactionService.makeWithdrawal(userId, amount);
    }

//    @GetMapping(path = ("/balance"))
//    public double getBalanceByAccountNumber(@RequestParam Long accountNumber) {
//        return userService.getBalanceByAccountNumber(accountNumber);
//    }
}
