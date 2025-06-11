package com.example.wallet.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность транзакции кошелька.
 * Представляет собой запись о финансовой операции, совершенной с кошельком.
 * Каждая транзакция содержит информацию о типе операции, сумме и связанном кошельке.
 */
@Data
@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {
    /**
     * Уникальный идентификатор транзакции
     */
    @Id
    private UUID id;

    /**
     * Кошелек, с которым связана транзакция
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    /**
     * Тип операции (пополнение или снятие)
     */
    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    /**
     * Сумма транзакции
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Дата и время создания транзакции
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
} 