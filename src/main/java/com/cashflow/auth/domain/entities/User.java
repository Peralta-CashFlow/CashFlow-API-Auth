package com.cashflow.auth.domain.entities;

import com.cashflow.auth.core.domain.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name = "profile", nullable = false)
    private Profile profile;

    @Override
    public List<RoleEnum> getAuthorities() {
        return profile.getRoles().stream()
                .map(Role::getRoleEnum)
                .toList();
    }

    @Override
    public String getUsername() {
        return firstName.concat(" ").concat(lastName);
    }
}
