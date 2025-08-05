package com.cashflow.auth.domain.entities;

import com.cashflow.auth.core.domain.enums.RoleEnum;
import com.cashflow.auth.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
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
    @Lob
    @Column
    private byte[] avatar;
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column
    private LocalDate birthday;
    @Column(name = "tax_number", length = 14)
    private String taxNumber;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private FinancialProfile financialProfile;

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
