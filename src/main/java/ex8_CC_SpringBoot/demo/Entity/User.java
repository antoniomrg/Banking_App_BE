package ex8_CC_SpringBoot.demo.Entity;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "password")
    private char[] password = "".toCharArray();

    @Column(name = "accountNumber")
    private Long accountNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "iban")
    private String iban;

    @Column(name = "balance")
    private double balance;

}
