package com.example.wallet.repository;

import com.example.wallet.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с транзакциями кошелька.
 * Предоставляет методы для доступа к данным транзакций в базе данных.
 * Использует JPA для работы с базой данных и наследует стандартные методы
 * CRUD-операций от JpaRepository.
 */
@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {
} 