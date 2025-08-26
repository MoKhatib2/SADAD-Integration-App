package com.example.SadadApi.services.impl;

import org.springframework.stereotype.Service;

import com.example.SadadApi.models.SadadRecord;
import com.example.SadadApi.services.FusionErpService;

@Service
public class FusionErpServiceImpl implements FusionErpService{

    @Override
    public boolean createInvoice(SadadRecord record) {
        return true;
    }

}
