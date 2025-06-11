package com.example.wallet.mapper;

import com.example.wallet.dto.WalletResponse;
import com.example.wallet.model.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Маппер для преобразования между сущностью Wallet и DTO WalletResponse.
 * Использует MapStruct для автоматической генерации кода преобразования.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletMapper {
    
    /**
     * Преобразует сущность Wallet в DTO WalletResponse.
     *
     * @param wallet сущность кошелька
     * @return DTO с информацией о кошельке
     */
    WalletResponse toResponse(Wallet wallet);
} 