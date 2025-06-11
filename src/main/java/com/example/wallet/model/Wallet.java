package com.example.wallet.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность кошелька.
 * Представляет собой электронный кошелек с балансом и историей транзакций.
 * Используется для хранения и управления денежными средствами пользователя.
 */
@Data
@Entity
@Table(name = "wallets")
public class Wallet {
    /**
     * Уникальный идентификатор кошелька
     */
    @Id
    private UUID id;

    /**
     * Текущий баланс кошелька
     */
    @Column(nullable = false)
    private BigDecimal balance;

    /**
     * Дата и время создания кошелька
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления кошелька
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
} 