package ru.sbrf.java.edu.sberbank.service;

import ru.sbrf.java.edu.sberbank.dto.*;

public interface BankAccountService {

    /**
     * Возвращает данные банковского аккаунта
     * @param id - accountID
     * @return - BankAccountResponse
     */
    BankAccountResponse getBankAccount(Long id);

    /**
     * Создание банковского аккаунта и привязка его к клиенту
     * @param personId - personId
     * @param request - CreateAccountRequest
     */
    void createBankAccount(Long personId, CreateAccountRequest request);

    /**
     * Передача денег между аккаунтами
     * @param id - accountID
     * @param request - SendMoneyRequest
     * @return - SendMoneyResponse - в теле остаток на счете
     */
    SendMoneyResponse sendMoneyToAnotherAccount(Long id, SendMoneyRequest request);

    /**
     * Пополнение средств определенного аккаунта
     * @param accountId - accountID
     * @param request - FillMoneyRequest
     */
    void fillMoney(Long accountId, FillMoneyRequest request);
}
