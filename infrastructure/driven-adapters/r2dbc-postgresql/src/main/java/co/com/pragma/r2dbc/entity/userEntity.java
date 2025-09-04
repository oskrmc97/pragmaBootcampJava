package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class userEntity {

    @Column
    @Id
    private BigInteger user_id;
    private String name;
    @Column("lastname")
    private String lastName;
    private LocalDate birthdate;
    private String address;
    private String phone;
    private String email;

    @Column("id_rol")
    private Integer rolUser;

    @Column("identity_document")
    private String document;
    private BigDecimal salary;
    @Column("password_hash")
    private String password;
}
