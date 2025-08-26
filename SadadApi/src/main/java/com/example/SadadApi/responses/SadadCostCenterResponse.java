package com.example.SadadApi.responses;

import java.math.BigDecimal;

public record SadadCostCenterResponse(Long id, String code, String description, BigDecimal percentage) {

}
