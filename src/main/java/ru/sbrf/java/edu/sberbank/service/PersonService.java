package ru.sbrf.java.edu.sberbank.service;

import ru.sbrf.java.edu.sberbank.dto.PersonDtoResponse;
import ru.sbrf.java.edu.sberbank.dto.RegisterPersonRequest;
import ru.sbrf.java.edu.sberbank.dto.UpdatePersonDto;

/**
 * Сервис по работе с Person
 */
public interface PersonService {

    /**
     * Метод, создающий новую сущность Person
     *
     * @param request - registerPersonRequest
     * @return PersonDtoResponse с данными новой сущности Person
     */
    PersonDtoResponse createPerson(RegisterPersonRequest request);

    /**
     * Метод, возвращающий существующую сущность Person
     *
     * @param id - идентификатор возвращаемой сущности
     * @return PersonDtoResponse с данными сущности Person
     */
    PersonDtoResponse getPerson(Long id);

    /**
     * Метод, обновляющий поля сущности Person, перетирает поле если не пришло соответствующее значение
     *
     * @param request - UpdatePersonDto
     * @return - PersonDtoResponse с данными обновленной сущности Person
     */
    PersonDtoResponse putPerson(UpdatePersonDto request, Long id);

    /**
     *
     * @param request -UpdatePersonDto
     * @param id - personID
     * @return - PersonDtoResponse
     */
    PersonDtoResponse patchPerson(UpdatePersonDto request, Long id);

    /**
     * Метод, помечающий сущность как удаленную
     *
     * @param id - personId
     */
    void deletePerson(Long id);

}
