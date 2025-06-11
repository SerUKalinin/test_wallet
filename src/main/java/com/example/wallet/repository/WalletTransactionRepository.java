package com.example.wallet.repository;

import com.example.wallet.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с транзакциями кошелька
 */
@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {
} 