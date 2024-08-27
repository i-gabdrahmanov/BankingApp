package ru.sbrf.java.edu.sberbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.java.edu.sberbank.dto.PersonDtoResponse;
import ru.sbrf.java.edu.sberbank.dto.RegisterPersonRequest;
import ru.sbrf.java.edu.sberbank.dto.UpdatePersonDto;
import ru.sbrf.java.edu.sberbank.exception.Sberception;
import ru.sbrf.java.edu.sberbank.service.PersonService;

@RestController
@Slf4j
@RequestMapping("api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping("register")
    public ResponseEntity<PersonDtoResponse> registerPerson(@RequestBody @Valid RegisterPersonRequest request) {
        log.info("Вызван метод registerPerson для ИНН: %s".formatted(request.getInn()));
        PersonDtoResponse response = personService.createPerson(request);
        log.info("Выполнен метод registerPerson для ИНН: %s".formatted(request.getInn()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDtoResponse> getPerson(@PathVariable Long id) {
        log.info("Вызван метод getPerson для id: %d".formatted(id));
        PersonDtoResponse response = personService.getPerson(id);
        log.info("Выполнен метод getPerson для id: %d".formatted(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/put")
    // put - обновляет все поля сущности, если поле null - перетирает. patch - обновляе только пришедшие поля
    public ResponseEntity<PersonDtoResponse> putPerson(@PathVariable Long id, @RequestBody @Valid UpdatePersonDto request) {
        log.info("Вызван метод getPerson для id: %d".formatted(id));
        PersonDtoResponse response = personService.putPerson(request, id);
        log.info("Выполнен метод putPerson для id: %d".formatted(id));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/patch")
    // put - обновляет все поля сущности, если поле null - перетирает. patch - обновляе только пришедшие поля
    public ResponseEntity<PersonDtoResponse> patchPerson(@PathVariable Long id, @RequestBody @Valid UpdatePersonDto request) {
        log.info("Вызван метод patchPerson для id: %d".formatted(id));
        PersonDtoResponse response = personService.patchPerson(request, id);
        log.info("Выполнен метод patchPerson для id: %d".formatted(id));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.info("Вызван метод deletePerson для id: %d".formatted(id));
        personService.deletePerson(id);
        log.info("Выполнен метод deletePerson для id: %d".formatted(id));
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({Sberception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public void sberceptionHandler(Sberception ex) {
        log.error(ex.getMessage());
    }
}
