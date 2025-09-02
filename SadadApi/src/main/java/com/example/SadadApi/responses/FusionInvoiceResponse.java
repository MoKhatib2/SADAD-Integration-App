package com.example.SadadApi.responses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FusionInvoiceResponse(Long id, String invoiceNumber, String supplierName, String invoiceType,  LocalDate invoiceDate, BigDecimal amount) {

}
