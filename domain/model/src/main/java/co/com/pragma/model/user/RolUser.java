package co.com.pragma.model.user;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RolUser {
    private Integer idRol;
    private String name;
    private String description;
}
