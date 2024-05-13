package ex8_CC_SpringBoot.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    private Long accountNumber;
    private String iban;
    private Double balance;
}
