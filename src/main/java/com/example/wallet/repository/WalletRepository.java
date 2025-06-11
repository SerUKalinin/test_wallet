package com.example.wallet.repository;

import com.example.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с кошельками.
 * Предоставляет методы для доступа к данным кошельков в базе данных.
 * Использует JPA для работы с базой данных и поддерживает пессимистическую блокировку
 * для обеспечения целостности данных при параллельных операциях.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    /**
     * Находит кошелек по ID с пессимистической блокировкой для обновления.
     * Метод используется для обеспечения атомарности операций с кошельком
     * в условиях параллельного доступа.
     *
     * @param id уникальный идентификатор кошелька
     * @return Optional, содержащий кошелек, если он найден
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.id = :id")
    Optional<Wallet> findByIdForUpdate(@Param("id") UUID id);
} 