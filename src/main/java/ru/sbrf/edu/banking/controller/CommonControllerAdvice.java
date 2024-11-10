package ru.sbrf.edu.banking.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sbrf.edu.banking.exception.Sberception;

@ControllerAdvice
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler({Sberception.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
    public void sberceptionHandler(Sberception ex) {
        log.error(ex.getMessage());
    }
}
