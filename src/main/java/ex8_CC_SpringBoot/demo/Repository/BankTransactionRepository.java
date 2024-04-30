package ex8_CC_SpringBoot.demo.Repository;

import ex8_CC_SpringBoot.demo.Entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository <BankTransaction, Long>  {
    @Query("SELECT bt FROM BankTransaction bt WHERE bt.user.userId = :userId ORDER BY bt.transactionDate DESC LIMIT 5")
    List<BankTransaction> getLastFiveTransactionsByUserId(@Param("userId") Long userId);
}
