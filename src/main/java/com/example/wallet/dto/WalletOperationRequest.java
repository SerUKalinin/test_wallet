package com.example.wallet.dto;

import com.example.wallet.model.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для запроса операции с кошельком
 */
@Data
public class WalletOperationRequest {
    @NotNull(message = "ID кошелька не может быть пустым")
    private UUID walletId;

    @NotNull(message = "Тип операции не может быть пустым")
    private OperationType operationType;

    @NotNull(message = "Сумма не может быть пустой")
    @Positive(message = "Сумма должна быть положительной")
    private BigDecimal amount;
} 