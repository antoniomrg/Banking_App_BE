package ex8_CC_SpringBoot.demo.Controller;


import ex8_CC_SpringBoot.demo.Service.BankTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("users/{userId}/bank-transactions")
@RestController
@Tag(name = "Bank Transactions", description = "Operations related to bank transactions")
public class BankTransactionController {

    private final BankTransactionService bankTransactionService;

    public BankTransactionController(BankTransactionService bankTransactionService) {
        this.bankTransactionService = bankTransactionService;
    }

    @Operation(
            summary = "Get the last 5 transactions for this bank account",
            responses = {
                    @ApiResponse(
                            description = "List of last 5 transactions retrieved successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid data provided",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "No transactions found for this bank account id",
                            responseCode = "404"
                    )
            })
    @GetMapping(path = "/history")
    public ResponseEntity<?> getLastFiveTransactions(@RequestParam Long bankAccountId) {
        return ResponseEntity.ok(bankTransactionService.getLastFiveTransactions(bankAccountId));
    }

    @Operation(
            summary = "Make a deposit to the bank account specified by the bank account id",
            responses = {
                    @ApiResponse(
                            description = "The amount has been added to the bank account",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No bank account found with the id provided",
                            responseCode = "404"
                    )
            })
    @PutMapping(path = "/deposit")
    public ResponseEntity<?> makeDeposit(@RequestParam Long bankAccountId, @RequestParam double amount) {
        bankTransactionService.makeDeposit(bankAccountId, amount);
        return ResponseEntity.ok().body("You added " + amount + " to your account");
    }

    @Operation(
            summary = "Make a withdrawal from the bank account specified by the bank account id",
            responses = {
                    @ApiResponse(
                            description = "The amount was withdrawn from the specified bank account",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Amount is greater than current  balance",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "No bank account found with the id provided",
                            responseCode = "404"
                    )
            })
    @PutMapping(path = "/withdrawal")
    public ResponseEntity<?> makeWithdrawal(@RequestParam Long bankAccountId, @RequestParam double amount) {
        bankTransactionService.makeWithdrawal(bankAccountId, amount);
        return ResponseEntity.ok().body("You withdrew " + amount + " from your account");
    }
}
