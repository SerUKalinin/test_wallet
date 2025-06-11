package com.example.wallet.exception;

import java.util.UUID;

/**
 * Исключение, возникающее при попытке снять сумму, превышающую баланс кошелька.
 * Выбрасывается, когда пользователь пытается выполнить операцию снятия средств,
 * но на кошельке недостаточно средств для выполнения операции.
 */
public class InsufficientFundsException extends RuntimeException {
    /**
     * Создает новое исключение с сообщением об ошибке.
     *
     * @param walletId ID кошелька, на котором недостаточно средств
     */
    public InsufficientFundsException(UUID walletId) {
        super(String.format("Недостаточно средств на кошельке %s", walletId));
    }
} 