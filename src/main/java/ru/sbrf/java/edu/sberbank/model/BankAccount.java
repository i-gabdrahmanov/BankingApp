package ru.sbrf.java.edu.sberbank.model;

import jakarta.persistence.*;
import lombok.*;
import ru.sbrf.java.edu.sberbank.enums.CurrencyEnum;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person person;

    @Column
    private BigDecimal currentSum = new BigDecimal(0);

    @Column
    private BigDecimal overdraftLimit = new BigDecimal(0);

    @Column
    private CurrencyEnum currency;
}
