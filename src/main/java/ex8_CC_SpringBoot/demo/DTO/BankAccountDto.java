package ex8_CC_SpringBoot.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    private Long accountNumber;
    private String iban;
    private Double balance;
}
