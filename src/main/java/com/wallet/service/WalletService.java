package com.wallet.service;

import com.wallet.dto.WalletOperationRequest;
import com.wallet.dto.WalletResponse;
import com.wallet.exception.InsufficientFundsException;
import com.wallet.exception.WalletNotFoundException;
import com.wallet.model.OperationType;
import com.wallet.model.Wallet;
import com.wallet.model.WalletTransaction;
import com.wallet.repository.WalletRepository;
import com.wallet.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сервис для работы с кошельками
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;

    /**
     * Выполняет операцию с кошельком
     *
     * @param request запрос на выполнение операции
     * @return информация о кошельке после операции
     */
    @Transactional
    public WalletResponse performOperation(WalletOperationRequest request) {
        log.info("Выполнение операции {} для кошелька {} на сумму {}", 
                request.getOperationType(), request.getWalletId(), request.getAmount());

        Wallet wallet = walletRepository.findByIdForUpdate(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(request.getWalletId()));

        BigDecimal newBalance = calculateNewBalance(wallet.getBalance(), request.getOperationType(), request.getAmount());
        
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Недостаточно средств на кошельке {} для операции {}", 
                    request.getWalletId(), request.getOperationType());
            throw new InsufficientFundsException(request.getWalletId());
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setId(UUID.randomUUID());
        transaction.setWallet(wallet);
        transaction.setOperationType(request.getOperationType());
        transaction.setAmount(request.getAmount());
        transactionRepository.save(transaction);

        log.info("Операция успешно выполнена. Новый баланс кошелька {}: {}", 
                request.getWalletId(), newBalance);

        return mapToResponse(wallet);
    }

    /**
     * Получает информацию о кошельке
     *
     * @param walletId ID кошелька
     * @return информация о кошельке
     */
    @Transactional(readOnly = true)
    public WalletResponse getWallet(UUID walletId) {
        log.info("Получение информации о кошельке {}", walletId);
        
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        return mapToResponse(wallet);
    }

    private BigDecimal calculateNewBalance(BigDecimal currentBalance, OperationType operationType, BigDecimal amount) {
        return operationType == OperationType.DEPOSIT
                ? currentBalance.add(amount)
                : currentBalance.subtract(amount);
    }

    private WalletResponse mapToResponse(Wallet wallet) {
        WalletResponse response = new WalletResponse();
        response.setId(wallet.getId());
        response.setBalance(wallet.getBalance());
        return response;
    }
} 