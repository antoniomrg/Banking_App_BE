package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;
import ex8_CC_SpringBoot.demo.Entity.User;
import java.util.List;

public interface BankTransactionService {
    void makeDeposit(Long userId, double amount);
    void makeWithdrawal(Long userId, double amount);
    void createAndLogBankTransaction(User user, double amount, BankTransactionType bankTransactionType);
    List<BankTransaction> getLastFiveTransactions(Long userId);

}
