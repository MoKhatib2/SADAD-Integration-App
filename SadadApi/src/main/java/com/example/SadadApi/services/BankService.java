package com.example.SadadApi.services;

import java.util.List;

import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;

public interface BankService extends CrudService{
    GenericResponse<List<CodeNameResponse>> findAllByOrganiztion(Long organizationId);
}
