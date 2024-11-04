package ru.sbrf.edu.sberbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.edu.sberbank.annotation.ExecutionLogger;
import ru.sbrf.edu.sberbank.dto.*;
import ru.sbrf.edu.sberbank.exception.Sberception;
import ru.sbrf.edu.sberbank.service.BankAccountService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    @ExecutionLogger
    public ResponseEntity<BankAccountResponse> getBankAccount(@Param("id") Long id) {
        BankAccountResponse response = bankAccountService.getBankAccount(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{personId}/create")
    public ResponseEntity<Void> createAccount(@PathVariable Long personId, @RequestBody @Valid CreateAccountRequest request) {
        log.info("Вызван метод createAccount с параметром personId: %d".formatted(personId));
        bankAccountService.createBankAccount(personId, request);
        log.info("Выполнен метод createAccount с параметром personId: %d".formatted(personId));
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

    @ExceptionHandler({Sberception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public void sberceptionHandler(Sberception ex) {
        log.error(ex.getMessage());
    }
}
