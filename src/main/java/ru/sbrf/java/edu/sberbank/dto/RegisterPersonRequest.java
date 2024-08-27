package ru.sbrf.java.edu.sberbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterPersonRequest {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String inn;
    // отчества у клиента может не быть
    private String patronymic;
}
