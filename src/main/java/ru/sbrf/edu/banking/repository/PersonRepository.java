package ru.sbrf.edu.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.edu.banking.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByInn(String inn);

}