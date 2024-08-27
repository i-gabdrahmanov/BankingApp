package ru.sbrf.edu.sberbank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sbrf.edu.sberbank.dto.*;
import ru.sbrf.edu.sberbank.exception.Sberception;
import ru.sbrf.edu.sberbank.mapper.BankAccountMapper;
import ru.sbrf.edu.sberbank.model.BankAccount;
import ru.sbrf.edu.sberbank.service.BankAccountService;
import ru.sbrf.edu.sberbank.repository.BankAccountRepository;
import ru.sbrf.edu.sberbank.repository.PersonRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final PersonRepository personRepository;
    private final BankAccountMapper mapper;

    @Override
    public BankAccountResponse getBankAccount(Long id) {
        // log.info();
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(
                () -> new Sberception(String.format("Банковский аккаунт с id: %d не найден", id))
        );
        // log.info();
        return mapper.toBankAccountResponse(bankAccount);
    }

    @Override
    public void createBankAccount(Long personId, CreateAccountRequest request) {
        BankAccount bankAccount = mapper.toBankAccount(request);
        bankAccount.setPerson(personRepository.getReferenceById(personId));
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public SendMoneyResponse sendMoneyToAnotherAccount(Long id, SendMoneyRequest request) {
        BankAccount targetAccount = bankAccountRepository.findById(id).orElseThrow(
                () -> new Sberception(String.format("Банковский аккаунт отправителя с id: %d не найден", id))
        );
        BankAccount personAccount = bankAccountRepository.findById(request.getRecipientId()).orElseThrow(
                () -> new Sberception(String.format("Банковский аккаунт получателя с id: %d не найден", request.getRecipientId()))
        );

        targetAccount.setCurrentSum(targetAccount.getCurrentSum().add(request.getTransferSum()));
        personAccount.setCurrentSum(personAccount.getCurrentSum().subtract(request.getTransferSum()));
        bankAccountRepository.save(targetAccount);
        personAccount = bankAccountRepository.save(personAccount);
        return new SendMoneyResponse(personAccount.getCurrentSum());
    }

    @Override
    public void fillMoney(Long accountId, FillMoneyRequest request) {
        BankAccount targetAccount = bankAccountRepository.findById(accountId).orElseThrow(
                () -> new Sberception(String.format("Банковский аккаунт для пополнения id: %d не найден", accountId))
        );
        targetAccount.setCurrentSum(targetAccount.getCurrentSum().add(request.getAppendSum()));
        bankAccountRepository.save(targetAccount);
    }

    // todo tests, heroku аналог
}