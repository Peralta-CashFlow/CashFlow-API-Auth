package com.cashflow.auth.repository.financial;

import com.cashflow.auth.domain.entities.FinancialProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialProfileRepository extends JpaRepository<FinancialProfile, Long> {
}
