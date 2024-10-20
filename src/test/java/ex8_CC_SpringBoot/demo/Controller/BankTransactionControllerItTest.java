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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BankTransactionControllerItTest {

    private static final Long ACCOUNT_NUMBER = 12345L;
    private static final Long BANK_ACCOUNT_ID = 1L;
    private static final Double BALANCE = 100.00;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    public void setUp() {
        bankTransactionRepository.deleteAll();
        bankAccountRepository.deleteAll();

        val transactionList = BankTransaction.builder()
                .transactionId(1L)
                .bankTransactionType(BankTransactionType.DEPOSIT)
                .build();

        val bankAccount = BankAccount.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .balance(BALANCE)
                .build();

        bankAccountRepository.save(bankAccount);
        bankTransactionRepository.save(transactionList);
    }

    @Test
    void getLastFiveTransactions() throws Exception {
        mockMvc.perform(get("/users/1/bank-transactions/history")
                        .param("bankAccountId", BANK_ACCOUNT_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(1L))
                .andExpect(jsonPath("$[0].bankTransactionType").value(BankTransactionType.DEPOSIT));

    }

    @Test
    void makeDeposit() {
    }

    @Test
    void makeWithdrawal() {
    }
}