package ex8_CC_SpringBoot.demo.DTO;

public record UserDTO (

         Long accountNumber,
         String name,
         String surname,
         String iban,
         double balance
){
}
