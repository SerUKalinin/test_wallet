package com.example.wallet.service;

import com.example.wallet.dto.WalletOperationRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.mapper.WalletMapper;
import com.example.wallet.model.OperationType;
import com.example.wallet.model.Wallet;
import com.example.wallet.model.WalletTransaction;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.repository.WalletTransactionRepository;
import com.example.wallet.exception.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сервис для работы с кошельками.
 * Предоставляет бизнес-логику для управления электронными кошельками,
 * включая операции пополнения, снятия средств и получения информации о кошельке.
 * Все операции выполняются в рамках транзакций для обеспечения целостности данных.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    /**
     * Репозиторий для работы с кошельками
     */
    private final WalletRepository walletRepository;
    
    /**
     * Репозиторий для работы с транзакциями
     */
    private final WalletTransactionRepository transactionRepository;

    /**
     * Маппер для преобразования между сущностями и DTO
     */
    private final WalletMapper walletMapper;

    /**
     * Выполняет операцию с кошельком (пополнение или снятие средств).
     * Операция выполняется в рамках транзакции с блокировкой строки для обеспечения
     * атомарности и изоляции операций.
     *
     * @param request запрос на выполнение операции, содержащий ID кошелька,
     *               тип операции и сумму
     * @return информация о кошельке после выполнения операции
     * @throws WalletNotFoundException если кошелек не найден
     * @throws InsufficientFundsException если недостаточно средств для снятия
     */
    @Transactional
    public WalletResponse performOperation(WalletOperationRequest request) {
        log.info("Выполнение операции {} для кошелька {} на сумму {}", 
                request.getOperationType(), request.getWalletId(), request.getAmount());

        Wallet wallet = walletRepository.findByIdForUpdate(request.getWalletId())
                .orElseThrow(() -> new WalletNotFoundException(request.getWalletId()));

        BigDecimal newBalance = calculateNewBalance(wallet.getBalance(), request.getOperationType(), request.getAmount());
        
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Недостаточно средств на кошельке {} для операции {}",
                    request.getWalletId(), request.getOperationType());
            throw new InsufficientFundsException(request.getWalletId());
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        createAndSaveTransaction(wallet, request.getOperationType(), request.getAmount());

        log.info("Операция успешно выполнена. Новый баланс кошелька {}: {}", 
                request.getWalletId(), newBalance);

        return walletMapper.toResponse(wallet);
    }

    /**
     * Получает информацию о кошельке по его ID.
     * Операция выполняется в режиме только для чтения.
     *
     * @param walletId ID кошелька
     * @return информация о кошельке
     * @throws WalletNotFoundException если кошелек не найден
     */
    @Transactional(readOnly = true)
    public WalletResponse getWallet(UUID walletId) {
        log.info("Получение информации о кошельке {}", walletId);
        
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        return walletMapper.toResponse(wallet);
    }

    /**
     * Рассчитывает новый баланс кошелька на основе текущего баланса,
     * типа операции и суммы.
     *
     * @param currentBalance текущий баланс кошелька
     * @param operationType тип операции (пополнение или снятие)
     * @param amount сумма операции
     * @return новый баланс кошелька
     */
    private BigDecimal calculateNewBalance(BigDecimal currentBalance, OperationType operationType, BigDecimal amount) {
        return operationType == OperationType.DEPOSIT
                ? currentBalance.add(amount)
                : currentBalance.subtract(amount);
    }

    /**
     * Создает и сохраняет транзакцию для указанного кошелька.
     *
     * @param wallet кошелек, для которого создается транзакция
     * @param operationType тип операции (пополнение или снятие)
     * @param amount сумма операции
     */
    private void createAndSaveTransaction(Wallet wallet, OperationType operationType, BigDecimal amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setId(UUID.randomUUID());
        transaction.setWallet(wallet);
        transaction.setOperationType(operationType);
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
    }
} 