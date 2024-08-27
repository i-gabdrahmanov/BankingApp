package ru.sbrf.edu.sberbank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    private String patronymic;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

    @Column(nullable = false)
    private boolean isPrivilege;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false, unique = true)
    private String inn;
}
