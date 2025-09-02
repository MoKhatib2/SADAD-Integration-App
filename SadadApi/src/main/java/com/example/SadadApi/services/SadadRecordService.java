package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.SadadRecordDto;
import com.example.SadadApi.dtos.SadadRecordStatusDto;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.responses.SadadRecordResponse;

public interface SadadRecordService {
    GenericResponse<SadadRecordResponse> createRecord(SadadRecordDto sadadRecordDto);
    GenericResponse<SadadRecordResponse> updateRecord(SadadRecordDto sadadRecordDto, Long id);
    GenericResponse<SadadRecordResponse> duplicateRecord(IdDto dto);
    GenericResponse<SadadRecordResponse> confirmRecord(Long id);
    GenericResponse<SadadRecordResponse> cancelRecord(Long id);
    GenericResponse<SadadRecordResponse> releaseRecord(Long id);
    GenericResponse<SadadRecordResponse> retryInvoiceRecord(Long id);
    GenericResponse<List<SadadRecordResponse>> findAll();
    GenericResponse<SadadRecordResponse> findById(Long id);
    GenericResponse<List<SadadRecordResponse>> findByStatus(SadadRecordStatusDto SadadRecordStatusDto);
}
