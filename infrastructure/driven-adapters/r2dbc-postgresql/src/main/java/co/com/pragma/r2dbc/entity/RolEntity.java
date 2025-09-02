package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigInteger;

@Getter
@Setter
@Table("rol")
@NoArgsConstructor
@AllArgsConstructor
public class RolEntity {
    @Column
    @Id
    private Integer id_rol;
    private String name;
    private String description;
}
