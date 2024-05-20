package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Repository.BankAccountRepository;
import ex8_CC_SpringBoot.demo.Repository.BankTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final BankAccountService bankAccountService;
    private final BankAccountRepository bankAccountRepository;

    public BankTransactionServiceImpl(BankTransactionRepository bankTransactionRepository,
                                      BankAccountService bankAccountService,
                                      BankAccountRepository bankAccountRepository)
                                      {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankAccountService = bankAccountService;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public void makeDeposit(Long bankAccountId, double amount) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new EntityNotFoundException());
        bankAccountService.updateBalance(bankAccount, amount);
        createAndLogBankTransaction(bankAccount, amount, BankTransactionType.DEPOSIT);
    }

    @Transactional
    @Override
    public void makeWithdrawal(Long bankAccountId, double amount) {
        BankAccount bankAccount= bankAccountRepository.findById(bankAccountId).orElseThrow(() -> new EntityNotFoundException());
        if (amount > bankAccount.getBalance()){
            throw new IllegalArgumentException();
        }
        bankAccountService.updateBalance(bankAccount, -amount);
        createAndLogBankTransaction(bankAccount, amount, BankTransactionType.WITHDRAWAL);
    }

    @Override
    public void createAndLogBankTransaction(BankAccount bankAccount, double amount, BankTransactionType bankTransactionType) {
        BankTransaction bankTransaction = BankTransaction.builder()
                .bankAccount(bankAccount)
                .amount(amount)
                .bankTransactionType(bankTransactionType)
                .transactionDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
        .build();
        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public List<BankTransaction> getLastFiveTransactions(Long bankAccountId) {

        List<BankTransaction> bankTransactions = bankTransactionRepository.getLastFiveTransactionsByAccountId(bankAccountId);

        if (bankTransactions.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return bankTransactions;
    }
}
