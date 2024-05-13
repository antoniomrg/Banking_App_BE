package ex8_CC_SpringBoot.demo.Repository;

import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("SELECT b.balance FROM BankAccount b WHERE b.accountNumber = :accountNumber")
    Optional<Double> findBalanceByAccountNumber(@Param("accountNumber") Long accountNumber);
}
