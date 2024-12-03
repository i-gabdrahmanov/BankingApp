package ru.sbrf.edu.banking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.edu.banking.annotation.ExecutionLogger;
import ru.sbrf.edu.banking.dto.*;
import ru.sbrf.edu.banking.service.BankAccountService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<BankAccountResponse> getBankAccount(@Param("id") Long id) {
        BankAccountResponse response = bankAccountService.getBankAccount(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{personId}/create")
    public ResponseEntity<Void> createAccount(@PathVariable Long personId,
                                              @RequestBody @Valid CreateAccountRequest request) {
        bankAccountService.createBankAccount(personId, request);
        return ResponseEntity.ok().build();
    }
    // todo put patch delete

    @PostMapping("{accountId}/sendTo")
    public ResponseEntity<SendMoneyResponse> sendMoneyToAnotherPerson(@PathVariable Long accountId, @RequestBody SendMoneyRequest request) {
        log.info("Вызван метод sendMoneyToAnotherPerson с параметром accountId: %d, recipientId: %d".formatted(accountId, request.getRecipientId()));
        SendMoneyResponse response = bankAccountService.sendMoneyToAnotherAccount(accountId, request);
        log.info("Выполнен метод sendMoneyToAnotherPerson с параметром accountId: %d, recipientId: %d".formatted(accountId, request.getRecipientId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("{accountId}/fillMoney")
    @ExecutionLogger
    public ResponseEntity<Void> fillMoney(@PathVariable Long accountId, @RequestBody FillMoneyRequest request) {
        bankAccountService.fillMoney(accountId, request);
        return ResponseEntity.ok().build();
    }


}
