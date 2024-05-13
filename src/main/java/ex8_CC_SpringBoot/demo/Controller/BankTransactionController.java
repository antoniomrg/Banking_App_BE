package ex8_CC_SpringBoot.demo.Controller;


import ex8_CC_SpringBoot.demo.Service.BankTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("users/{userId}/bank-transactions")
@RestController
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @GetMapping(path = "/history")
    public ResponseEntity<?> getLastFiveTransactions(@RequestParam Long bankAccountId) {
        return ResponseEntity.ok(bankTransactionService.getLastFiveTransactions(bankAccountId));
    }

    @PutMapping(path = "/deposit")
    public ResponseEntity<?> makeDeposit(@RequestParam Long bankAccountId, @RequestParam double amount) {
        bankTransactionService.makeDeposit(bankAccountId, amount);
        return ResponseEntity.ok().body("You added " + amount + " to your account");
    }

    @PutMapping(path = "/withdrawal")
    public ResponseEntity<?> makeWithdrawal(@RequestParam Long bankAccountId, @RequestParam double amount) {
        bankTransactionService.makeWithdrawal(bankAccountId, amount);
        return ResponseEntity.ok().body("You withdrew " + amount + " from your account");
    }
}
