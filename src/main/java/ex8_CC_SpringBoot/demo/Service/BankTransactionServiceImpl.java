package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.BankTransactionRepository;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public BankTransactionServiceImpl(BankTransactionRepository bankTransactionRepository,
                                      UserService userService,
                                      UserRepository userRepository){
        this.bankTransactionRepository = bankTransactionRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void makeDeposit(Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userService.updateBalance(user, amount);
        createAndLogBankTransaction(user, amount, BankTransactionType.DEPOSIT);
    }

    @Transactional
    @Override
    public void makeWithdrawal(Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (amount > user.getBalance()){
            throw new IllegalArgumentException("Insufficient balance");
        }
        userService.updateBalance(user, -amount);
        createAndLogBankTransaction(user, amount, BankTransactionType.WITHDRAWAL);
    }

    @Override
    public void createAndLogBankTransaction(User user, double amount, BankTransactionType bankTransactionType) {
        // @Builder pattern
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setUser(user);
        bankTransaction.setBankTransactionType(bankTransactionType);
        bankTransaction.setAmount(amount);
        bankTransactionRepository.save(bankTransaction);
        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public List<BankTransaction> getLastFiveTransactions(Long userId) {

        List<BankTransaction> allBankTransactions = bankTransactionRepository.findAllByUserId(userId);

        if (allBankTransactions.isEmpty()) {
            throw new EntityNotFoundException("userId not found.");
        }

        if (allBankTransactions.size() > 5) {
            List<BankTransaction> lastFiveBankTransactions =
                    allBankTransactions.subList(allBankTransactions.size()-5, allBankTransactions.size());
            return lastFiveBankTransactions;
        }
        return allBankTransactions;
    }


}
