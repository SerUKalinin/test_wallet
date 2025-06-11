package com.example.wallet.exception;

import java.util.UUID;

/**
 * Исключение, возникающее при попытке выполнить операцию с несуществующим кошельком
 */
public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(UUID walletId) {
        super(String.format("Кошелек с ID %s не найден", walletId));
    }
} 