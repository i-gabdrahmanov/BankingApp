package ru.sbrf.java.edu.sberbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sbrf.java.edu.sberbank.dto.BankAccountResponse;
import ru.sbrf.java.edu.sberbank.dto.CreateAccountRequest;
import ru.sbrf.java.edu.sberbank.model.BankAccount;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "currentSum", source = "currentSum")
    @Mapping(target = "overdraftLimit", source = "overdraftLimit")
    @Mapping(target = "currency", source = "currency")
    BankAccountResponse toBankAccountResponse(BankAccount bankAccount);

    BankAccount toBankAccount(CreateAccountRequest request);
}