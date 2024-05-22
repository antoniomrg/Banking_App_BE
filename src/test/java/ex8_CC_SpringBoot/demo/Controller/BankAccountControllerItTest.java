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

        val user = User.builder()
//                .userId(USER_ID)  // Specify the ID directly
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

        val userTest = userRepository.save(user);
        USER_ID = userTest.getUserId();
        bankAccountRepository.save(bankAccount);
    }

    @Test
    void addBankAccountById() throws Exception {
        InputStream inputStream = FileUtils.openInputStream(new File("C:\\Users\\black\\Dropbox (Personale)\\Coding\\Aitho\\Backender 101\\Ex_8_CC_SpringBoot\\demo\\src\\test\\resources\\utils\\bankAccount.json"));

        String inputStreamContent = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        System.out.println("InputStream content:");
        System.out.println(inputStreamContent);

        // Reset the InputStream for ObjectMapper since it has already been read
        inputStream = FileUtils.openInputStream(new File("C:\\Users\\black\\Dropbox (Personale)\\Coding\\Aitho\\Backender 101\\Ex_8_CC_SpringBoot\\demo\\src\\test\\resources\\utils\\bankAccount.json"));

        // Deserialize the InputStream to BankAccountDto
        BankAccountDto bankAccountDto = new ObjectMapper().readValue(inputStream, BankAccountDto.class);

        // Print the BankAccountDto object
        System.out.println("BankAccountDto content:");
        System.out.println(bankAccountDto);


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
}