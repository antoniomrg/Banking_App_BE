package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.DTO.BankAccountDto;
import ex8_CC_SpringBoot.demo.DTO.BankAccountMapper;
import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.BankAccountRepository;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BankAccountMapper bankAccountMapper;

    BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
                           UserRepository userRepository,
                           BankAccountMapper bankAccountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    public void addBankAccountById(BankAccountDto bankAccountDto, Long userId){
       User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
       BankAccount bankAccount = bankAccountMapper.fromDto(bankAccountDto);
       bankAccount.setUser(user);
       bankAccountRepository.save(bankAccount);
    }

    public Double getBalanceByAccountNumber(Long accountNumber) {
        return bankAccountRepository.findBalanceByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException());
    }

    public void updateBalance(BankAccount bankAccount, Double amount) {
        double currentBalance = bankAccount.getBalance();
        double newBalance = currentBalance + amount;
        bankAccount.setBalance(newBalance);
        bankAccountRepository.save(bankAccount);
    }
}
