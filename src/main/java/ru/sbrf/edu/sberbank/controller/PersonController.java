package ru.sbrf.edu.sberbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbrf.edu.sberbank.annotation.ExecutionLogger;
import ru.sbrf.edu.sberbank.dto.RegisterPersonRequest;
import ru.sbrf.edu.sberbank.dto.UpdatePersonDto;
import ru.sbrf.edu.sberbank.exception.Sberception;
import ru.sbrf.edu.sberbank.service.PersonService;
import ru.sbrf.edu.sberbank.dto.PersonDtoResponse;

@RestController
@Slf4j
@RequestMapping("api/person")
@RequiredArgsConstructor
@ExecutionLogger
public class PersonController {
    private final PersonService personService;

    @PostMapping("register")
    public ResponseEntity<PersonDtoResponse> registerPerson(@RequestBody @Valid RegisterPersonRequest request) {
        PersonDtoResponse response = personService.createPerson(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PersonDtoResponse> getPerson(@Param("id") Long id) {
        PersonDtoResponse response = personService.getPerson(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/put")
    // put - обновляет все поля сущности, если поле null - перетирает. patch - обновляе только пришедшие поля
    public ResponseEntity<PersonDtoResponse> putPerson(@PathVariable Long id, @RequestBody @Valid UpdatePersonDto request) {
        PersonDtoResponse response = personService.putPerson(request, id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/patch")
    // put - обновляет все поля сущности, если поле null - перетирает. patch - обновляе только пришедшие поля
    public ResponseEntity<PersonDtoResponse> patchPerson(@PathVariable Long id, @RequestBody @Valid UpdatePersonDto request) {
        PersonDtoResponse response = personService.patchPerson(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({Sberception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public void sberceptionHandler(Sberception ex) {
        log.error(ex.getMessage());
    }
}
