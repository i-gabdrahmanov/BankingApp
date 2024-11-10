package ru.sbrf.edu.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.edu.banking.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
