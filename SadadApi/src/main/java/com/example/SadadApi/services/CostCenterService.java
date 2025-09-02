package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.responses.SadadCostCenterResponse;

public interface CostCenterService extends CrudService{
    GenericResponse<List<SadadCostCenterResponse>> findAllBySadadRecord(Long id);
}