package com.example.wallet.dto;

import com.example.wallet.model.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для запроса операции с кошельком.
 * Используется для передачи данных о запрашиваемой операции
 * между клиентом и сервером.
 */
@Data
public class WalletOperationRequest {
    /**
     * Уникальный идентификатор кошелька, с которым выполняется операция
     */
    @NotNull(message = "ID кошелька не может быть пустым")
    private UUID walletId;

    /**
     * Тип операции (пополнение или снятие средств)
     */
    @NotNull(message = "Тип операции не может быть пустым")
    private OperationType operationType;

    /**
     * Сумма операции. Должна быть положительным числом
     */
    @NotNull(message = "Сумма не может быть пустой")
    @Positive(message = "Сумма должна быть положительной")
    private BigDecimal amount;
} 