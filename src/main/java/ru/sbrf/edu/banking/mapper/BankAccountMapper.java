package ru.sbrf.edu.banking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sbrf.edu.banking.dto.CreateAccountRequest;
import ru.sbrf.edu.banking.dto.BankAccountResponse;
import ru.sbrf.edu.banking.model.BankAccount;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    @Mapping(target = "currentSum", source = "currentSum")
    @Mapping(target = "overdraftLimit", source = "overdraftLimit")
    @Mapping(target = "currency", source = "currency")
    BankAccountResponse toBankAccountResponse(BankAccount bankAccount);

    BankAccount toBankAccount(CreateAccountRequest request);
}