package com.wallet.exception;

import java.util.UUID;

/**
 * Исключение, возникающее при попытке снять сумму, превышающую баланс кошелька
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(UUID walletId) {
        super(String.format("Недостаточно средств на кошельке %s", walletId));
    }
} 