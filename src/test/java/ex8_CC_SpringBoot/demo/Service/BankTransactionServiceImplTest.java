package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.BankAccountRepository;
import ex8_CC_SpringBoot.demo.Repository.BankTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BankTransactionServiceImplTest {

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankTransactionServiceImpl bankTransactionServiceImpl;

    @Test
    void makeDeposit_Ok() {
        when(bankAccountRepository.findById(any())).thenReturn(Optional.of(buildFakeBankAccount()));

        bankTransactionServiceImpl.makeDeposit(any(), 100.00);

        assertEquals(200.00, buildFakeBankAccount().getBalance() + 100.00);
    }

    @Test
    void makeWithdrawal_Ok() {
        when(bankAccountRepository.findById(any())).thenReturn(Optional.of(buildFakeBankAccount()));

        bankTransactionServiceImpl.makeWithdrawal(any(), 50.00);

        assertEquals(50.00, buildFakeBankAccount().getBalance() - 50.00);
    }

    @Test
    void makeWithdrawal_AmountIsGreaterThanBalance(){
        when(bankAccountRepository.findById(any())).thenReturn(Optional.of(buildFakeBankAccount()));

        assertThrows(IllegalArgumentException.class, () -> {

            bankTransactionServiceImpl.makeWithdrawal(any(), 99999999999999.00);
        });

    }

    @Test
    void createAndLogBankTransaction() {
        bankTransactionServiceImpl.createAndLogBankTransaction(buildFakeBankAccount(), 100.00, BankTransactionType.DEPOSIT);

        verify(bankTransactionRepository).save(buildFakeBankTransaction());
    }

    @Test
    void getLastFiveTransactions_Ok() {

        when(bankTransactionRepository.getLastFiveTransactionsByAccountId(any())).thenReturn(List.of(buildFakeBankTransaction()));

        val out = bankTransactionServiceImpl.getLastFiveTransactions(any());

        val firstElement = out.get(0);
        assertNotNull(firstElement);
        assertEquals(buildFakeBankAccount(), firstElement.getBankAccount());

    }

    @Test
    void getLastFiveTransactions_NotFound(){

        List<BankTransaction> emptyList = null;

        assertThrows(EntityNotFoundException.class, () -> {

            bankTransactionServiceImpl.getLastFiveTransactions(any());
        });

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

    private BankTransaction buildFakeBankTransaction(){
        return BankTransaction.builder()
                .bankAccount(buildFakeBankAccount())
                .amount(100.00)
                .bankTransactionType(BankTransactionType.DEPOSIT)
                .transactionDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }


    private Double fakeBigAmount = 99999999.99;
}