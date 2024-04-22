package ex8_CC_SpringBoot.demo.Repository;

import ex8_CC_SpringBoot.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
    @Query("SELECT u.balance FROM User u WHERE u.accountNumber = :accountNumber")
    Double findBalanceByAccountNumber(@Param("accountNumber") Long accountNumber);

}
