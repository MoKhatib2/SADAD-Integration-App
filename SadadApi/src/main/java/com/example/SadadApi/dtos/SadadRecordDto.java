package com.example.SadadApi.dtos;

import java.math.BigDecimal;
import java.util.List;

public record SadadRecordDto(
    Long id,
    String organizationCode,
    String legalEntityCode,
    String remitterBankCode,
    String remitterBankAccount,
    String billerCode,
    String vendorCode,
    String vendorSiteCode,
    String invoiceTypeCode,
    String invoiceNumebr,
    String subscriptionAccountNumber,
    BigDecimal amount,
    String expenseAccountCode,
    String businessCode,
    String locationCode,
    List<String> costCentersCodes
) {

}
