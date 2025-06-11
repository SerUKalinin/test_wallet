package com.example.wallet.exception;

import java.util.UUID;

/**
 * Исключение, возникающее при попытке выполнить операцию с несуществующим кошельком.
 * Выбрасывается, когда пользователь пытается выполнить операцию с кошельком,
 * который не существует в системе.
 */
public class WalletNotFoundException extends RuntimeException {
    /**
     * Создает новое исключение с сообщением об ошибке.
     *
     * @param walletId ID кошелька, который не был найден
     */
    public WalletNotFoundException(UUID walletId) {
        super(String.format("Кошелек с ID %s не найден", walletId));
    }
} 