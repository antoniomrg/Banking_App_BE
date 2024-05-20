package ex8_CC_SpringBoot.demo.Service;

import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import ex8_CC_SpringBoot.demo.Entity.BankTransactionType;

import java.util.List;
import java.util.Optional;

public interface BankTransactionService {
    void makeDeposit(Long bankAccountId, double amount);
    void makeWithdrawal(Long bankAccountId, double amount);
    void createAndLogBankTransaction(BankAccount bankAccount, double amount, BankTransactionType bankTransactionType);
    List<BankTransaction> getLastFiveTransactions(Long bankAccountId);
}
