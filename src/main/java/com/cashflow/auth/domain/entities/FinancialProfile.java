package com.cashflow.auth.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user_financial_profile")
public class FinancialProfile implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(length = 50)
    private String occupation;
    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;
    @Column(name = "monthly_expenses_limit")
    private BigDecimal monthlyExpensesLimit;
    @Column(length = 100)
    private String goals;

}
