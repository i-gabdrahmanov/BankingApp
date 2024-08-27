package ru.sbrf.java.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.java.edu.sberbank.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByInn(String inn);

}