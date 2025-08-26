package com.example.SadadApi.dtos;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SadadRecordDto(
    @NotNull Long organizationId,
    @NotNull Long legalEntityId,
    @NotNull Long remitterBankId,
    @NotNull Long remitterBankAccountId,
    @NotNull Long billerId,
    @NotNull Long vendorId,
    @NotNull Long vendorSiteId,
    @NotNull Long invoiceTypeId,
    @NotNull String invoiceNumebr,
    @NotNull String subscriptionAccountNumber,
    @NotNull @DecimalMin("0.00") BigDecimal amount,
    @NotNull Long expenseAccountId,
    @NotNull Long businessId,
    @NotNull Long locationId,
    @NotEmpty List<@Valid CostCenterDto> costCenters
) {
    public record CostCenterDto(@NotNull Long costCenterId, @NotNull @DecimalMin("0.00") @DecimalMax("100.00") BigDecimal percentage) {
    }
}
