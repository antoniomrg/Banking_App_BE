package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Service.BankAccountService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users/{userId}/bank-accounts")
@RestController
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<?> addBankAccountById(@RequestBody BankAccountDto bankAccountDto, @PathVariable Long userId) {
        bankAccountService.addBankAccountById(bankAccountDto, userId);
        return ResponseEntity.ok("Bank account added successfully");
    }

    @GetMapping(path = ("/balance"))
    public ResponseEntity<Double> getBalanceByAccountNumber(@RequestParam Long accountNumber) {
        return ResponseEntity.ok(bankAccountService.getBalanceByAccountNumber(accountNumber));
    }
}
