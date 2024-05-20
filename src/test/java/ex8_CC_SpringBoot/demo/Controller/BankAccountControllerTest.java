package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Service.BankAccountService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @Test
    void addBankAccountById() {
        val response = bankAccountController.addBankAccountById(buildFakeBankAccountDto(), 1L);
        assertEquals("Bank account added successfully", response.getBody());
    }

    @Test
    void getBalanceByAccountNumber() {
        when(bankAccountService.getBalanceByAccountNumber(any())).thenReturn(buildFakeBankAccountDto().getBalance());

        val response = bankAccountController.getBalanceByAccountNumber(buildFakeBankAccountDto().getAccountNumber());

        assertEquals(buildFakeBankAccountDto().getBalance(), response.getBody());
    }

    private User buildFakeUser(){
        return User.builder()
                .name("Name")
                .surname("Surname")
                .address("Address")
                .phoneNumber("Phone Number")
                .fiscalCode("Fiscal code")
                .build();
    }

    private BankAccount buildFakeBankAccount(){
        return BankAccount.builder()
                .user(buildFakeUser())
                .accountNumber(00000L)
                .iban("Iban")
                .balance(100.00)
                .internalId(123456).
                build();
    }

    private BankAccountDto buildFakeBankAccountDto(){
        return BankAccountDto.builder()
                .accountNumber(000000L)
                .iban("Iban")
                .balance(100.00)
                .build();
    }
}