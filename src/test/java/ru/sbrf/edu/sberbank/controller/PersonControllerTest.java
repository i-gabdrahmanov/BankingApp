package ru.sbrf.edu.sberbank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.sbrf.edu.sberbank.dto.RegisterPersonRequest;
import ru.sbrf.edu.sberbank.dto.UpdatePersonDto;
import ru.sbrf.edu.sberbank.exception.Sberception;
import ru.sbrf.edu.sberbank.service.PersonService;
import ru.sbrf.edu.sberbank.dto.PersonDtoResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testRegisterPerson() throws Exception {
        PersonDtoResponse response = new PersonDtoResponse();
        response.setPersonId(1L);
        response.setName("name");
        response.setSurname("surname");
        response.setPatronymic("patronymic");
        response.setInn("12345");
        response.setPrivilege(true);

        when(personService.createPerson(any(RegisterPersonRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/person/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "name": "name",
                                        "surname": "surname",
                                        "inn": "12345"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.personId").value(1L))
                .andExpect(jsonPath("$.surname").value("surname"))
                .andExpect(jsonPath("$.patronymic").value("patronymic"))
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.privilege").value(true));

        verify(personService, times(1)).createPerson(any(RegisterPersonRequest.class));
    }

    @Test
    public void testThrowExceptionRegisterPerson() throws Exception {
        mockMvc.perform(post("/api/person/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "name": "name"
                                }
                                """))
                .andExpect(status().isBadRequest());
        verify(personService, times(0)).createPerson(any(RegisterPersonRequest.class));
    }


    @Test
    public void testGetPerson() throws Exception {
        Long id = 1L;
        PersonDtoResponse response = new PersonDtoResponse();
        response.setPersonId(1L);
        response.setName("name");
        response.setSurname("surname");
        response.setPatronymic("patronymic");
        response.setInn("12345");
        response.setPrivilege(true);

        when(personService.getPerson(id)).thenReturn(response);

        mockMvc.perform(get("/api/person/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.personId").value(1L))
                .andExpect(jsonPath("$.surname").value("surname"))
                .andExpect(jsonPath("$.patronymic").value("patronymic"))
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.privilege").value(true));

        verify(personService, times(1)).getPerson(id);
    }

    @Test
    public void testThrowGetPerson() throws Exception {
        Mockito.doThrow(new Sberception("test")).when(personService).getPerson(anyLong());

        long id = 1L;
        mockMvc.perform(get("/api/person/" + id)
                        .content("""
                                {
                                        "name": "name"
                                }
                                """))
                .andExpect(status().isIAmATeapot());
        verify(personService, times(0)).createPerson(any(RegisterPersonRequest.class));
    }

    @Test
    public void testPutPerson() throws Exception {
        Long id = 1L;

        PersonDtoResponse response = new PersonDtoResponse();
        response.setInn("12345");
        response.setPrivilege(true);

        when(personService.putPerson(any(UpdatePersonDto.class), eq(id))).thenReturn(response);

        mockMvc.perform(put("/api/person/" + id + "/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "name": "name",
                                        "surname": "surname",
                                        "inn": "12345",
                                        "isPrivilege": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inn").value("12345"))
                .andExpect(jsonPath("$.privilege").value(true));

        verify(personService, times(1)).putPerson(any(UpdatePersonDto.class), eq(id));
    }

    @Test
    public void testThrowPutPerson() throws Exception {
        Long id = 1L;

        mockMvc.perform(put("/api/person/" + id + "/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "name": "name"
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).putPerson(any(UpdatePersonDto.class), eq(id));
    }

    @Test
    public void testPatchPerson() throws Exception {
        Long id = 1L;

        PersonDtoResponse response = new PersonDtoResponse();
        response.setPersonId(1L);
        response.setName("name");

        when(personService.patchPerson(any(UpdatePersonDto.class), eq(id))).thenReturn(response);

        mockMvc.perform(patch("/api/person/" + id + "/patch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "name": "name",
                                        "surname": "surname",
                                        "inn": "12345",
                                        "isPrivilege": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(1L))
                .andExpect(jsonPath("$.name").value("name"));

        verify(personService, times(1)).patchPerson(any(UpdatePersonDto.class), eq(id));
    }

    @Test
    public void testThrowPatchPerson() throws Exception {
        Long id = 1L;

        mockMvc.perform(patch("/api/person/" + id + "/patch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                        "name": "name",
                                        "surname": "surname"
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(personService, times(0)).patchPerson(any(UpdatePersonDto.class), eq(id));
    }

    @Test
    public void testDeletePerson() throws Exception {
        Long id = 1L;

        doNothing().when(personService).deletePerson(id);

        mockMvc.perform(delete("/api/person/" + id + "/delete"))
                .andExpect(status().isOk());

        verify(personService, times(1)).deletePerson(id);
    }

    @Test
    public void testThrowDeletePerson() throws Exception {
        Long id = 1L;

        doThrow(new Sberception("test")).when(personService).deletePerson(id);

        mockMvc.perform(delete("/api/person/" + id + "/delete"))
                .andExpect(status().isIAmATeapot());

        verify(personService, times(1)).deletePerson(id);
    }
}



