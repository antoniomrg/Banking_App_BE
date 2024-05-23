package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Repository.BankAccountRepository;
import ex8_CC_SpringBoot.demo.Repository.BankTransactionRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IT_Test_BankTransactionController {

    private static final Long ACCOUNT_NUMBER = 12345L;
    private static final Double BALANCE = 100.00;
    private static final Double AMOUNT  = 200.00;
    private static Long BANK_ACCOUNT_ID = 1L;
    private static final BankTransactionType BANK_TRANSACTION_TYPE = BankTransactionType.DEPOSIT;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void getLastFiveTransactions() throws Exception {
        commonOperations(1L);

        mockMvc.perform(get("/users/1/bank-transactions/history")
                .param("bankAccountId", BANK_ACCOUNT_ID.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("S[0].bankTransactionType").value(BANK_TRANSACTION_TYPE))
                .andExpect((jsonPath("S[0].amount").value(AMOUNT)));
    }

    @Test
    void makeDeposit() {
    }

    @Test
    void makeWithdrawal() {
    }

    private void commonOperations(Long id) {
        bankTransactionRepository.deleteAll();

        var bankAccount = BankAccount.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .balance(BALANCE)
                .build();

        val bankTransaction = BankTransaction.builder()
                .transactionId(id)
                .bankTransactionType(BankTransactionType.DEPOSIT)
                .amount(AMOUNT)
                .transactionDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        bankAccountRepository.save(bankAccount);
        bankTransactionRepository.save(bankTransaction);
    }
}