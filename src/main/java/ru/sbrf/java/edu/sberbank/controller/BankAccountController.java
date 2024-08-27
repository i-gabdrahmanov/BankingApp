package ru.sbrf.java.edu.sberbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.java.edu.sberbank.dto.*;
import ru.sbrf.java.edu.sberbank.exception.Sberception;
import ru.sbrf.java.edu.sberbank.service.BankAccountService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable Long id) {
        log.info("Вызван метод getBankAccount с параметром id: %d".formatted(id));
        BankAccountResponse response = bankAccountService.getBankAccount(id);
        log.info("Выполнен метод getBankAccount с параметром id: %d".formatted(id));
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
    public ResponseEntity<Void> fillMoney(@PathVariable Long accountId, @RequestBody FillMoneyRequest request) {
        log.info("Вызван метод fillMoney с параметром accountId: %d, appendSum: %f".formatted(accountId, request.getAppendSum()));
        bankAccountService.fillMoney(accountId, request);
        log.info("Выполнен метод fillMoney с параметром accountId: %d, appendSum: %f".formatted(accountId, request.getAppendSum()));

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({Sberception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public void sberceptionHandler(Sberception ex) {
        log.error(ex.getMessage());
    }
}
