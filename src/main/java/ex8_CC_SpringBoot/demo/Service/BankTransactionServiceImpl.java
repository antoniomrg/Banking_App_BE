package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Entity.User;
import ex8_CC_SpringBoot.demo.Repository.BankTransactionRepository;
import ex8_CC_SpringBoot.demo.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        userService.updateBalance(user, amount);
        createAndLogBankTransaction(user, amount, BankTransactionType.DEPOSIT);
    }

    @Transactional
    @Override
    public void makeWithdrawal(Long userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        if (amount > user.getBalance()){
            throw new IllegalArgumentException();
        }
        userService.updateBalance(user, -amount);
        createAndLogBankTransaction(user, amount, BankTransactionType.WITHDRAWAL);
    }

    @Override
    public void createAndLogBankTransaction(User user, double amount, BankTransactionType bankTransactionType) {
        BankTransaction bankTransaction = BankTransaction.builder()
                .user(user)
                .amount(amount)
                .bankTransactionType(bankTransactionType)
                .transactionDate(LocalDateTime.now())
        .build();
        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public List<BankTransaction> getLastFiveTransactions(Long userId) {

        List<BankTransaction> bankTransactions = bankTransactionRepository.getLastFiveTranscationsByUserId(userId);

        if (bankTransactions.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return bankTransactions;
    }
}
