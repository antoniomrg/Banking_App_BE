package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;

import java.util.Optional;

public interface BankAccountService {
    void addBankAccountById(BankAccountDto bankAccountDto, Long userId);
    Double getBalanceByAccountNumber(Long accountNumber);
    void updateBalance(BankAccount bankAccount, Double amount);
}
