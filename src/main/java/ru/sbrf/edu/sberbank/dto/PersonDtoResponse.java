package ru.sbrf.edu.sberbank.dto;

import lombok.Data;

@Data
public class PersonDtoResponse {
    private Long personId;
    private String name;
    private String surname;
    private String patronymic;
    private String inn;
    private boolean isPrivilege;
    private boolean isDeleted;
}
