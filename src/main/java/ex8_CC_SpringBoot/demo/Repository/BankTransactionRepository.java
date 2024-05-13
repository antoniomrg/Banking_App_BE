package ex8_CC_SpringBoot.demo.Repository;

import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository <BankTransaction, Long>  {
    @Query("SELECT bt FROM BankTransaction bt WHERE bt.bankAccount.bankAccountId = :bankAccountId ORDER BY bt.transactionDate DESC LIMIT 5")
    List<BankTransaction> getLastFiveTransactionsByAccountId(@Param("bankAccountId") Long bankAccountId);
}
