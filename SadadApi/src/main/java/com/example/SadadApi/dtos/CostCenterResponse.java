package com.example.SadadApi.dtos;

import java.math.BigDecimal;

public record CostCenterResponse(String code, String description, BigDecimal percentage) {

}
