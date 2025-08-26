package com.example.SadadApi.responses;

import java.math.BigDecimal;

public record CostCenterResponse(Long id, String code, String description, BigDecimal percentage) {

}
