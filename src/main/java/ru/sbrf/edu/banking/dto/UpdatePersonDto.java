package ru.sbrf.edu.banking.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatePersonDto extends RegisterPersonRequest {
    private boolean isPrivilege;
}
