package ex8_CC_SpringBoot.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.BankAccountRepository;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BankAccountControllerItTest {

    private static Long USER_ID = 0L;
    private static final Long USER_ID_NOT_EXISTING = 999L;
    private static final Long ACCOUNT_NUMBER = 12345L;
    private static final Double BALANCE = 100.00;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();

        var user = User.builder()
                .name("Test User")
                .surname("Test Surname")
                .address("123 Test St")
                .phoneNumber("1234567890")
                .fiscalCode("TSTUSR1234")
                .build();

        val bankAccount = BankAccount.builder()
                        .accountNumber(ACCOUNT_NUMBER)
                        .balance(BALANCE)
                        .build();

        user = userRepository.save(user);
        USER_ID = user.getUserId();
        bankAccountRepository.save(bankAccount);
    }

    @Test
    void addBankAccountById() throws Exception {
        InputStream inputStream = FileUtils.openInputStream(new File("C:\\Users\\black\\Dropbox (Personale)\\Coding\\Aitho\\Backender 101\\Ex_8_CC_SpringBoot\\demo\\src\\test\\resources\\utils\\bankAccount.json"));

        BankAccountDto bankAccountDto = new ObjectMapper().readValue(inputStream, BankAccountDto.class);

        mockMvc.perform(post("/users/{userId}/bank-accounts", USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(bankAccountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Bank account added successfully"));
    }

    @Test
    void getBalanceByAccountNumber() throws Exception {
        mockMvc.perform(get("/users/1/bank-accounts/balance")
                        .param("accountNumber", ACCOUNT_NUMBER.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(BALANCE.toString()));
    }

    @Test
    void getBalanceByAccountNumber_AccountNumberIsString () throws Exception {
        mockMvc.perform(get("/users/1/bank-accounts/balance")
                        .param("accountNumber", "AAAAAAAAA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}