package com.example.wallet.controller;

import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Контроллер для работы с кошельками.
 * Предоставляет REST API для управления электронными кошельками,
 * включая операции пополнения, снятия средств и получения информации о кошельке.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Wallet Controller", description = "API для управления кошельками")
public class WalletController {
    /**
     * Сервис для работы с кошельками
     */
    private final WalletService walletService;

    /**
     * Выполняет операцию с кошельком (пополнение или снятие средств).
     *
     * @param request Данные операции, включающие ID кошелька, тип операции и сумму
     * @return ResponseEntity с информацией о результате операции
     */
    @PostMapping("/wallet")
    @Operation(summary = "Выполнить операцию с кошельком", 
               description = "Позволяет выполнить операции пополнения или снятия средств с кошелька")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Операция выполнена успешно"),
        @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
        @ApiResponse(responseCode = "404", description = "Кошелек не найден"),
        @ApiResponse(responseCode = "409", description = "Недостаточно средств")
    })
    public ResponseEntity<WalletResponse> performOperation(
            @Parameter(description = "Данные операции") @Valid @RequestBody WalletOperationRequest request) {
        return ResponseEntity.ok(walletService.performOperation(request));
    }

    /**
     * Получает информацию о кошельке по его ID.
     *
     * @param walletId Уникальный идентификатор кошелька
     * @return ResponseEntity с информацией о кошельке
     */
    @GetMapping("/wallets/{walletId}")
    @Operation(summary = "Получить информацию о кошельке", 
               description = "Возвращает текущий баланс и информацию о кошельке")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Информация получена успешно"),
        @ApiResponse(responseCode = "404", description = "Кошелек не найден")
    })
    public ResponseEntity<WalletResponse> getWallet(
            @Parameter(description = "ID кошелька") @PathVariable UUID walletId) {
        return ResponseEntity.ok(walletService.getWallet(walletId));
    }
} 