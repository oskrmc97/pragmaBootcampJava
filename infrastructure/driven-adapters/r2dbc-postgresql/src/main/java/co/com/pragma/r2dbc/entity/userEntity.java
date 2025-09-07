package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class userEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = switch (rolUser) {
            case 1 -> "ADMIN";
            case 2 -> "CLIENT";
            case 3 -> "ADVISER";
            default -> throw new IllegalStateException("Unexpected value: " + rolUser);
        };
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
