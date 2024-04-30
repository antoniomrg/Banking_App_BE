package ex8_CC_SpringBoot.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long accountNumber;
    private String name;
    private String surname;
    private String iban;
    private double balance;
}
