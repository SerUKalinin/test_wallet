package com.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для ответа с информацией о кошельке
 */
@Data
public class WalletResponse {
    private UUID id;
    private BigDecimal balance;
} 