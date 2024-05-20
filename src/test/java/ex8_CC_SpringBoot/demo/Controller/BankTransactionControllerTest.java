package ex8_CC_SpringBoot.demo.Controller;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Service.BankTransactionService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankTransactionControllerTest {

    @Mock
    private BankTransactionService bankTransactionService;

    @InjectMocks
    private BankTransactionController bankTransactionController;

    @Test
    void getLastFiveTransactions() {
      val fakeList = List.of(buildFakeBankTransaction());

      when(bankTransactionService.getLastFiveTransactions(any())).thenReturn(fakeList);

      val response = bankTransactionController.getLastFiveTransactions(1L);

      assertEquals(fakeList, response.getBody());
    }

    @Test
    void makeDeposit() {
        Long fakeBankAccountId = 1L;
        Double fakeAmount = 100.00;
        String expectedMessage = "You added " + fakeAmount + " to your account";

        val response = bankTransactionController.makeDeposit(fakeBankAccountId, 100.00);

        assertEquals(expectedMessage, response.getBody());

    }

    @Test
    void makeWithdrawal() {
        Long fakeBankAccountId = 1L;
        Double fakeAmount = 100.00;
        String expectedMessage = "You withdrew " + fakeAmount + " from your account";

        val response = bankTransactionController.makeWithdrawal(fakeBankAccountId, 100.00);

        assertEquals(expectedMessage, response.getBody());

    }

    private BankTransaction buildFakeBankTransaction(){
        return BankTransaction.builder()
                .bankAccount(buildFakeBankAccount())
                .amount(100.00)
                .bankTransactionType(BankTransactionType.DEPOSIT)
                .transactionDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
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

    private User buildFakeUser(){
        return User.builder()
                .name("Name")
                .surname("Surname")
                .address("Address")
                .phoneNumber("Phone Number")
                .fiscalCode("Fiscal code")
                .build();
    }
}