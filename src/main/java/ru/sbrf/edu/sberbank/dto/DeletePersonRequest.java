package ru.sbrf.edu.sberbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeletePersonRequest {
    @NotNull
    private Long personId;
}
