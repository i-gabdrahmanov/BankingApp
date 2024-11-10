package ru.sbrf.edu.banking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeletePersonRequest {
    @NotNull
    private Long personId;
}
