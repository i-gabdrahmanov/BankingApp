package ru.sbrf.java.edu.sberbank.exception;

import lombok.Getter;

@Getter
public class Sberception extends RuntimeException {
    public Sberception(String message) {
        super(message);
    }
}
