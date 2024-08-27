package ru.sbrf.edu.sberbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.edu.sberbank.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByInn(String inn);

}