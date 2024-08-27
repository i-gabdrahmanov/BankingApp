package ru.sbrf.edu.sberbank.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdatePersonDto extends RegisterPersonRequest {
    private boolean isPrivilege;
}
