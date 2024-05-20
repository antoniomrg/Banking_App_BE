package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.DTO.BankAccountMapper;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.BankAccountRepository;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountServiceimpl;

    @Test
    void addBankAccountById() {

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(fakeUser()));
        when(bankAccountMapper.fromDto(any())).thenReturn(fakeBankAccount());

        bankAccountServiceimpl.addBankAccountById(fakeBankAccountDto(), fakeUser().getUserId());

        verify(bankAccountRepository).save(fakeBankAccount());
    }

    @Test
    void getBalanceByAccountNumber(){
        when(bankAccountRepository.findBalanceByAccountNumber(fakeBankAccount()
                .getAccountNumber())).thenReturn(Optional.ofNullable(fakeBankAccount().getBalance()));

        val out = bankAccountServiceimpl.getBalanceByAccountNumber(fakeBankAccount().getAccountNumber());

        assertNotNull(out);
        assertEquals(100.00, out);
    }

    @Test
    void updateBalance(){
        BankAccount fakeBankAccount = new BankAccount();
        fakeBankAccount.setBalance(100.00);

        bankAccountServiceimpl.updateBalance(fakeBankAccount, 50.00);

        assertEquals(150.00, fakeBankAccount.getBalance());
        verify(bankAccountRepository).save(any());
    }

    private User fakeUser(){
        return User.builder()
                .name("Name")
                .surname("Surname")
                .address("Address")
                .phoneNumber("Phone Number")
                .fiscalCode("Fiscal code")
                .build();
    }

    private BankAccount fakeBankAccount(){
        return BankAccount.builder()
                .user(fakeUser())
                .accountNumber(00000L)
                .iban("Iban")
                .balance(100.00)
                .internalId(123456).
                build();
    }

    private BankAccountDto fakeBankAccountDto(){
        return BankAccountDto.builder()
                .accountNumber(000000L)
                .iban("Iban")
                .balance(100.00)
                .build();
    }
}