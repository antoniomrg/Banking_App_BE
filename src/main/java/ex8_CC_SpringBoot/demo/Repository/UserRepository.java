package ex8_CC_SpringBoot.demo.Repository;

import ex8_CC_SpringBoot.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{

}
