package com.example.SadadApi.dtos;

import java.math.BigDecimal;
import java.util.List;

public record SadadRecordResponse(
    Long id,
    CodeNameResponse organization,
    String legalEntity,
    String remitterBank,
    CodeNameResponse remitterBankAccount,
    CodeNameResponse biller,
    CodeNameResponse vendor,
    CodeNameResponse vendorSite,
    String invoiceType,
    String invoiceNumebr,
    String subscriptionAccountNumber,
    BigDecimal amount,
    CodeNameResponse expenseAccount,
    CodeNameResponse business,
    CodeNameResponse location,
    List<CostCenterResponse> costCenters
) {

}
