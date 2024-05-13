package ex8_CC_SpringBoot.demo.DTO;

import ex8_CC_SpringBoot.demo.Entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public BankAccountDto toDto(BankAccount bankAccount) {

        return new BankAccountDto(
        bankAccount.getAccountNumber(),
        bankAccount.getIban(),
        bankAccount.getBalance()
        );
    }

    public BankAccount fromDto(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = new BankAccount();

        bankAccount.setAccountNumber(bankAccountDto.getAccountNumber());
        bankAccount.setIban(bankAccountDto.getIban());
        bankAccount.setBalance(bankAccountDto.getBalance());
        bankAccount.setInternalId((int) (Math.random() * 900000) + 100000);

        return bankAccount;
    }
}
