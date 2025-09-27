package com.bank.fx_commission.presentation.web.dto;

import com.bank.fx_commission.domain.Transaction;

import java.time.format.DateTimeFormatter;

public record TransactionResponseDTO(
        String id,
        String sourceAccountId,
        String destinationAccountId,
        double amountInBaseCurrency,
        String baseCurrency,
        double amountInQuoteCurrency,
        String quoteCurrency,
        double baseToQuoteRate,
        double baseToReferenceRate,
        String referenceCurrency,
        double commission,
        String title,
        String createdAt,
        String updatedAt,
        String status
) {
    public static TransactionResponseDTO fromTransaction(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId().toString(),
                transaction.getSourceAccountId().toString(),
                transaction.getDestinationAccountId().toString(),
                transaction.getAmountInBaseCurrency().doubleValue(),
                transaction.getBaseCurrency().toString(),
                transaction.getAmountInQuoteCurrency().doubleValue(),
                transaction.getQuoteCurrency().toString(),
                transaction.getBaseToReferenceRate().doubleValue(),
                transaction.getBaseToQuoteRate().doubleValue(),
                transaction.getReferenceCurrency().toString(),
                transaction.getCommission().doubleValue(),
                transaction.getTitle(),
                transaction.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                transaction.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                transaction.getStatus().toString()
        );
    }
}
