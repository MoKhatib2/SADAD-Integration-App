package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.dtos.GenericResponse;
import com.example.SadadApi.dtos.IdDto;
import com.example.SadadApi.dtos.SadadRecordDto;
import com.example.SadadApi.dtos.SadadRecordResponse;
import com.example.SadadApi.dtos.SadadRecordStatusDto;

public interface SadadService {
    GenericResponse<SadadRecordResponse> createRecord(SadadRecordDto sadadRecordDto);
    GenericResponse<SadadRecordResponse> updateRecord(SadadRecordDto sadadRecordDto);
    GenericResponse<SadadRecordResponse> duplicateRecord(IdDto idDto);
    GenericResponse<SadadRecordResponse> confirmRecord(IdDto idDto);
    GenericResponse<SadadRecordResponse> cancelRecord(IdDto idDto);
    GenericResponse<SadadRecordResponse> releaseRecord(IdDto idDto);
    GenericResponse<List<SadadRecordResponse>> findAll();
    GenericResponse<SadadRecordResponse> findById();
    GenericResponse<List<SadadRecordResponse>> findByStatus(SadadRecordStatusDto SadadRecordStatusDto);
}
