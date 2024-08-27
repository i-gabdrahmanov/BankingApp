package ru.sbrf.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.edu.sberbank.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
