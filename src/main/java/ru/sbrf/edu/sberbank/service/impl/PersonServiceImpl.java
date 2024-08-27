package ru.sbrf.edu.sberbank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sbrf.edu.sberbank.exception.Sberception;
import ru.sbrf.edu.sberbank.model.Person;
import ru.sbrf.edu.sberbank.dto.PersonDtoResponse;
import ru.sbrf.edu.sberbank.dto.RegisterPersonRequest;
import ru.sbrf.edu.sberbank.dto.UpdatePersonDto;
import ru.sbrf.edu.sberbank.repository.PersonRepository;
import ru.sbrf.edu.sberbank.service.PersonService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public PersonDtoResponse createPerson(RegisterPersonRequest request) {
        if (!personRepository.existsByInn(request.getInn())) {
            Person person = Person.builder()
                    .name(request.getName())
                    .surname(request.getSurname())
                    .patronymic(request.getPatronymic())
                    .inn(request.getInn())
                    .build();
            return createPersonDtoResponse(personRepository.save(person));
        } else {
            throw new Sberception("Пользователь с таким ИНН уже зарегистрирован");
        }
    }

    @Override
    public PersonDtoResponse getPerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new Sberception(String.format("Пользователь с id: %d не найден", id))
        );
        return createPersonDtoResponse(person);
    }

    @Override
    public PersonDtoResponse putPerson(UpdatePersonDto request, Long id) {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new Sberception(String.format("Пользователь с id: %d не найден", id))
        );
        person.setName(request.getName());
        person.setSurname(request.getSurname());
        person.setPatronymic(request.getPatronymic());
        person.setPrivilege(request.isPrivilege());
        person.setInn(request.getInn());
        return createPersonDtoResponse(personRepository.save(person));
    }

    @Override
    public PersonDtoResponse patchPerson(UpdatePersonDto request, Long id) {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new Sberception(String.format("Пользователь с id: %d не найден", id))
        );
        Field[] requestFields = request.getClass().getSuperclass().getDeclaredFields();
        List<Field> childFieldslist = Arrays.stream(requestFields).toList();
        List<Field> parentFieldslist = Arrays.stream(request.getClass().getDeclaredFields()).toList();

        ArrayList<Field> fields = new ArrayList<>(parentFieldslist);
        fields.addAll(childFieldslist);
        for (Field requestField : fields) {
            requestField.setAccessible(true); // Делаем поле доступным
            try {
                Object value = requestField.get(request);
                if (value != null) { // Проверяем, что поле не null
                    Field userField = Person.class.getDeclaredField(requestField.getName());
                    userField.setAccessible(true);
                    userField.set(person, value); // Обновляем поле сущности
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return createPersonDtoResponse(personRepository.save(person));
    }

    @Override
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(
                () -> new Sberception(String.format("Пользователь с id: %d не найден", id))
        );
        person.setDeleted(true);
        personRepository.save(person);
    }

    private PersonDtoResponse createPersonDtoResponse(Person person) {
        PersonDtoResponse response = new PersonDtoResponse();
        response.setPersonId(person.getId());
        response.setName(person.getName());
        response.setSurname(person.getSurname());
        response.setPatronymic(person.getPatronymic());
        response.setPrivilege(person.isPrivilege());
        response.setDeleted(person.isDeleted());
        response.setInn(person.getInn());
        return response;
    }
}
