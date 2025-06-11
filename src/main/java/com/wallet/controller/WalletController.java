package com.wallet.controller;

import com.wallet.dto.WalletOperationRequest;
import com.wallet.dto.WalletResponse;
import com.wallet.service.WalletService;
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
 * Контроллер для работы с кошельками
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Wallet Controller", description = "API для управления кошельками")
public class WalletController {
    private final WalletService walletService;

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