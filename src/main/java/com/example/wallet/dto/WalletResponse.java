package com.example.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для ответа с информацией о кошельке.
 * Используется для передачи данных о кошельке
 * от сервера к клиенту.
 */
@Data
public class WalletResponse {
    /**
     * Уникальный идентификатор кошелька
     */
    private UUID id;

    /**
     * Текущий баланс кошелька
     */
    private BigDecimal balance;
} 