package com.wallet.controller;

import com.example.wallet.controller.WalletController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.model.OperationType;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тесты для контроллера кошелька.
 * Проверяет корректность работы API эндпоинтов для операций с кошельком.
 */
@WebMvcTest(WalletController.class)
@DisplayName("Тесты контроллера кошелька")
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    /**
     * Тест успешного выполнения операции с кошельком.
     * Проверяет, что при корректном запросе на пополнение кошелька:
     * - возвращается статус 200 OK
     * - в ответе содержится корректный ID кошелька
     * - в ответе содержится корректный баланс
     */
    @Test
    @DisplayName("Успешное выполнение операции с кошельком")
    void performOperation_ValidRequest_ReturnsOk() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(walletId);
        request.setOperationType(OperationType.DEPOSIT);
        request.setAmount(new BigDecimal("1000"));

        WalletResponse response = new WalletResponse();
        response.setId(walletId);
        response.setBalance(new BigDecimal("1000"));

        when(walletService.performOperation(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    /**
     * Тест успешного получения информации о кошельке.
     * Проверяет, что при запросе информации о существующем кошельке:
     * - возвращается статус 200 OK
     * - в ответе содержится корректный ID кошелька
     * - в ответе содержится корректный баланс
     */
    @Test
    @DisplayName("Успешное получение информации о кошельке")
    void getWallet_ValidId_ReturnsOk() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletResponse response = new WalletResponse();
        response.setId(walletId);
        response.setBalance(new BigDecimal("1000"));

        when(walletService.getWallet(walletId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    /**
     * Тест обработки некорректного запроса операции с кошельком.
     * Проверяет, что при отправке запроса без обязательных полей:
     * - возвращается статус 400 Bad Request
     */
    @Test
    @DisplayName("Обработка некорректного запроса операции")
    void performOperation_InvalidRequest_ReturnsBadRequest() throws Exception {
        WalletOperationRequest request = new WalletOperationRequest();
        // Не устанавливаем обязательные поля

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 