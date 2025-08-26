package com.example.SadadApi.responses;

import java.math.BigDecimal;
import java.util.List;


public record SadadRecordResponse(
    Long id,
    CodeNameResponse organization,
    CodeNameResponse legalEntity,
    CodeNameResponse remitterBank,
    BankAccountResponse remitterBankAccount,
    CodeNameResponse biller,
    CodeNameResponse vendor,
    CodeNameResponse vendorSite,
    CodeNameResponse invoiceType,
    String invoiceNumber,
    String subscriptionAccountNumber,
    BigDecimal amount,
    CodeNameResponse expenseAccount,
    CodeNameResponse business,
    CodeNameResponse location,
    List<CostCenterResponse> costCenters,
    String status
) {

}
