package ru.sbrf.java.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.java.edu.sberbank.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
