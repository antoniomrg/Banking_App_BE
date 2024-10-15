package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users/{userId}/bank-accounts")
@RestController
@Tag(name = "Bank Accounts", description = "Operations related to bank accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Operation(
            summary = "Add a new bank account for the user with the id being provided",
            responses = {
                    @ApiResponse(
                            description = "Bank account successfully added",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid data provided",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "User id not found",
                            responseCode = "404"
                    )
            })
    @PostMapping
    public ResponseEntity<?> addBankAccountById(@RequestBody BankAccountDto bankAccountDto, @PathVariable Long userId) {
        bankAccountService.addBankAccountById(bankAccountDto, userId);
        return ResponseEntity.ok("Bank account added successfully");
    }


    @Operation(
            summary = "Get the balance of the bank account whose account number is being provided",
            responses = {
                    @ApiResponse(
                            description = "Balance found successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid data provided",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "No Bank account found with this account number",
                            responseCode = "404"
                    )
            })
    @GetMapping(path = ("/balance"))
    public ResponseEntity<Double> getBalanceByAccountNumber(@RequestParam Long accountNumber) {
        return ResponseEntity.ok(bankAccountService.getBalanceByAccountNumber(accountNumber));
    }
}
