package ru.sbrf.edu.sberbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.sbrf.edu.sberbank.dto.*;
import ru.sbrf.edu.sberbank.enums.CurrencyEnum;
import ru.sbrf.edu.sberbank.exception.Sberception;
import ru.sbrf.edu.sberbank.service.BankAccountService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith({MockitoExtension.class})
public class BankAccountControllerTest {

    @InjectMocks
    private BankAccountController bankAccountController;

    @Mock
    private BankAccountService bankAccountService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(bankAccountController).build();
    }

    @Test
    public void testGetBankAccount() throws Exception {
        Long accountId = 1L;
        BankAccountResponse response = new BankAccountResponse(BigDecimal.valueOf(2.0),
                BigDecimal.valueOf(1.0), CurrencyEnum.USD);
        when(bankAccountService.getBankAccount(accountId)).thenReturn(response);

        mockMvc.perform(get("/api/account?id=" + accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentSum").value(new BigDecimal("2.0")));

        verify(bankAccountService).getBankAccount(accountId);
    }

    @Test
    public void testThrowGetBankAccount() throws Exception {
        Long accountId = 1L;

        Mockito.doThrow(new Sberception("test")).when(bankAccountService).getBankAccount(any());
        mockMvc.perform(get("/api/account?id=" + accountId))
                .andExpect(status().isIAmATeapot());

        verify(bankAccountService, times(1)).getBankAccount(accountId);
    }

    @Test
    public void testCreateAccount() throws Exception {
        Long personId = 1L;
        CreateAccountRequest request = new CreateAccountRequest(new BigDecimal(3), CurrencyEnum.USD);

        mockMvc.perform(post("/api/account/{personId}/create", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(bankAccountService, times(1)).createBankAccount(eq(personId), any(CreateAccountRequest.class));
    }

    @Test
    public void testThrowCreateAccount() throws Exception {
        Long personId = 1L;
        CreateAccountRequest request = new CreateAccountRequest(null, null);

        mockMvc.perform(post("/api/account/{personId}/create", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(bankAccountService, times(0)).createBankAccount(eq(personId), any(CreateAccountRequest.class));
    }

    @Test
    public void testSendMoneyToAnotherPerson() throws Exception {
        Long accountId = 1L;
        SendMoneyRequest request = new SendMoneyRequest();
        SendMoneyResponse response = new SendMoneyResponse(BigDecimal.valueOf(1.3));
        when(bankAccountService.sendMoneyToAnotherAccount(eq(accountId), any(SendMoneyRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/account/{accountId}/sendTo", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentSum").value(new BigDecimal("1.3")));

        verify(bankAccountService).sendMoneyToAnotherAccount(eq(accountId), any(SendMoneyRequest.class));
    }

    @Test
    public void testFillMoney() throws Exception {
        Long accountId = 1L;
        FillMoneyRequest request = new FillMoneyRequest();

        mockMvc.perform(post("/api/account/{accountId}/fillMoney", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(bankAccountService).fillMoney(any(), any(FillMoneyRequest.class));
    }

    @Test
    public void testThrowFillMoney() throws Exception {
        Long accountId = 1L;
        FillMoneyRequest request = new FillMoneyRequest();

        doThrow(new Sberception("test")).when(bankAccountService).fillMoney(anyLong(), any(FillMoneyRequest.class));

        mockMvc.perform(post("/api/account/{accountId}/fillMoney", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isIAmATeapot());

        verify(bankAccountService, times(1)).fillMoney(any(), any(FillMoneyRequest.class));
    }

    @Test
    public void testSberceptionHandler() throws Exception {
        when(bankAccountService.getBankAccount(any())).thenThrow(new Sberception("Test exception"));

        mockMvc.perform(get("/api/account?id=876", 1L))
                .andExpect(status().isIAmATeapot());

    }
}
